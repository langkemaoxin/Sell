package com.rex.sell.service;

import com.rex.sell.dataobject.OrderMaster;
import com.rex.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 16:13
 * @Version 1.0
 */
public interface OrderService {

    //创建订单
    OrderDTO create(OrderDTO orderDTO);

    //查询某个订单
    OrderDTO findOne(String orderId);

    //查询订单列表
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);

    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(OrderDTO orderDTO);

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);
}
