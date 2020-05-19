package com.rex.sell.controller;

import com.rex.sell.VO.ResultVO;
import com.rex.sell.converter.OrderForm2OrderDTOConverter;
import com.rex.sell.dto.OrderDTO;
import com.rex.sell.enums.ResultEnum;
import com.rex.sell.exception.SellException;
import com.rex.sell.form.OrderForm;
import com.rex.sell.service.OrderService;
import com.rex.sell.utils.ResultVOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BuyerOrderController
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/17 18:01
 * @Version 1.0
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确,orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());

        return ResultVOUtils.success(map);
    }

    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openId") String openId,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {

        if(StringUtils.isEmpty(openId)){
            log.error("【查询订单详情】openId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        Pageable pageable= PageRequest.of(page,size);
        Page<OrderDTO> list = orderService.findList(openId, pageable);

        return ResultVOUtils.success(list.getContent());
    }

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openId,
                                     @RequestParam("orderid") String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        return ResultVOUtils.success(orderDTO);
    }

    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openId,
                                     @RequestParam("orderid") String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        orderService.cancel(orderDTO);
        return ResultVOUtils.success();
    }
}































