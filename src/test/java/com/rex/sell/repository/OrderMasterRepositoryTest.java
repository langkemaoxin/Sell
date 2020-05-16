package com.rex.sell.repository;

import com.rex.sell.dataobject.OrderMaster;
import com.rex.sell.enums.OrderStatusEnum;
import com.rex.sell.enums.PayStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@SpringBootTest
class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("0001");
        orderMaster.setBuyerAddress("成都");
        orderMaster.setBuyerName("小妹");
        orderMaster.setBuyerOpenid("110011");
        orderMaster.setBuyerPhone("15225214545");
        orderMaster.setOrderAmount(BigDecimal.valueOf(1));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        OrderMaster save = repository.save(orderMaster);
        Assert.notNull(save,"");
    }

    @Test
    void findByOpenId() {
        PageRequest pageRequest =   PageRequest.of(0,10);
        Page<OrderMaster> openIdabc = repository.findByBuyerOpenid("110011", pageRequest);
        Assert.isTrue(openIdabc.getTotalElements()>0,"");
    }
}