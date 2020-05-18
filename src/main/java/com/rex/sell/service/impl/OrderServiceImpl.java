package com.rex.sell.service.impl;

import com.rex.sell.converter.OrderMaster2OrderDTOConvert;
import com.rex.sell.dataobject.Cart;
import com.rex.sell.dataobject.OrderDetail;
import com.rex.sell.dataobject.OrderMaster;
import com.rex.sell.dataobject.ProductInfo;
import com.rex.sell.dto.OrderDTO;
import com.rex.sell.enums.OrderStatusEnum;
import com.rex.sell.enums.PayStatusEnum;
import com.rex.sell.enums.ResultEnum;
import com.rex.sell.exception.SellException;
import com.rex.sell.repository.OrderDetailRepository;
import com.rex.sell.repository.OrderMasterRepository;
import com.rex.sell.service.OrderService;
import com.rex.sell.service.ProductService;
import com.rex.sell.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 16:22
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = IdWorker.GenerateId();
        BigDecimal totalPrice = BigDecimal.ZERO;

        //1. 查询商品
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //累计金额 .add(totalPrice);
            totalPrice = productInfo.getProductPrice()
                    .multiply(BigDecimal.valueOf(orderDetail.getProductQuantity()))
                    .add(totalPrice);

            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(IdWorker.GenerateId());

            //保存到订单详情中
            orderDetailRepository.save(orderDetail);
        }

        //3. 写入订单数据库()
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);

        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(totalPrice);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setOrderStatusStr(OrderStatusEnum.NEW.getMessage());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setPayStatusStr(PayStatusEnum.WAIT.getMessage());
        orderMasterRepository.save(orderMaster);

        //4. 扣库存
        List<Cart> cartList = orderDTO.getOrderDetailList()
                .stream()
                .map(o -> new Cart(o.getProductId(), o.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> optional = orderMasterRepository.findById(orderId);
        OrderMaster orderMaster = optional.get();
        if(orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        orderDTO.setOrderDetailList(orderDetails);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> orderDTOS =OrderMaster2OrderDTOConvert.convert(orderMasters.getContent());
        Page<OrderDTO> result = new PageImpl<>(orderDTOS,orderMasters.getPageable(),orderMasters.getTotalElements());
        return result;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {

        orderDTO =findOne(orderDTO.getOrderId());
        if(orderDTO==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        //恢复商品的库存
        List<Cart> cartList = orderDTO.getOrderDetailList().stream().map(o -> new Cart(o.getProductId(), o.getProductQuantity()))
                .collect(Collectors.toList());

        productService.increaseStock(cartList);

        //如果已经支付了，需要退款
        if(orderDTO.getPayStatus()==PayStatusEnum.SUCCESS.getCode()){

        }

        Optional<OrderMaster> masterOptional = orderMasterRepository.findById(orderDTO.getOrderId());

        OrderMaster orderMaster=masterOptional.get();

        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMaster.setOrderStatusStr(OrderStatusEnum.CANCEL.getMessage());
        orderMasterRepository.save(orderMaster);

        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("订单【{}】状态异常",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("订单【{}】更新失败",orderMaster);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {

        //判断订单的完结状态 只有新订单才能够进行支付
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单状态不正确】{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付状态不正确】{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【订单支付完成】,{}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        return orderDTO;
    }
}
