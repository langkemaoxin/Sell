package com.rex.sell.repository;

import com.rex.sell.dataobject.OrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("0001");
        orderDetail.setOrderId("0001");
        orderDetail.setProductId("123456");
        orderDetail.setProductName("牙膏");
        orderDetail.setProductPrice(BigDecimal.valueOf(100));
        orderDetail.setProductQuantity(1);
        OrderDetail save = repository.save(orderDetail);

        Assert.notNull(save,"");
    }

    @Test
    void findByOrderId() {
        List<OrderDetail> byOrderId = repository.findByOrderId("0001");
        Assert.isTrue(byOrderId.size()>0,"");


    }
}