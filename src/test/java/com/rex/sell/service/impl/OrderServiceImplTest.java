package com.rex.sell.service.impl;

import com.rex.sell.dataobject.OrderDetail;
import com.rex.sell.dto.OrderDTO;
import com.rex.sell.enums.OrderStatusEnum;
import com.rex.sell.enums.PayStatusEnum;
import com.rex.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("rex");
        orderDTO.setBuyerPhone("135464");
        orderDTO.setBuyerAddress("成都zxxxdifang");
        orderDTO.setBuyerOpenid("openid12345679");

        List<OrderDetail> orderDetails=new ArrayList<>();

        for (int i = 1; i <=3; i++) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductId(String.valueOf(i));
            orderDetail.setProductQuantity(10000);
            orderDetails.add(orderDetail);
        }

        orderDTO.setOrderDetailList(orderDetails);

        OrderDTO dto = orderService.create(orderDTO);
        Assert.notNull(dto,"");


    }

    @Test
    void findOne() {
        OrderDTO one = orderService.findOne("1261594804672925696");
        System.out.println(one);
        Assert.notNull(one,"");
    }

    @Test
    void findList() {
        Page<OrderDTO> orderDTOS = orderService.findList("openid12345679", PageRequest.of(0, 100));
        System.out.println(orderDTOS);
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findOne("1261594804672925696");
        orderDTO = orderService.cancel(orderDTO);
        Assert.isTrue(orderDTO.getOrderStatus()== OrderStatusEnum.CANCEL.getCode(),"");
    }

    @Test
    void finish() {
        OrderDTO orderDTO = orderService.findOne("1261594804672925696");
        orderDTO = orderService.finish(orderDTO);
        Assert.isTrue(orderDTO.getOrderStatus()== OrderStatusEnum.FINISHED.getCode(),"");
    }

    @Test
    void paid() {
        OrderDTO orderDTO = orderService.findOne("1261594867956584448");
        orderDTO = orderService.paid(orderDTO);
        Assert.isTrue(orderDTO.getPayStatus()== PayStatusEnum.SUCCESS.getCode(),"");
    }
}