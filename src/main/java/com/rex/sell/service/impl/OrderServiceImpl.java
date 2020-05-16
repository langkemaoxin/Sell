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
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
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
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}