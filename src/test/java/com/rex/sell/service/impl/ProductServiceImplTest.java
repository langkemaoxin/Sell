package com.rex.sell.service.impl;

import com.rex.sell.dataobject.ProductInfo;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void findOne() {
        ProductInfo one = productService.findOne("1234545");
        Assert.notNull(one,"");
    }

    @Test
    void findUpAll() {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.isTrue(upAll.size()!=0,"");
    }

    @Test
    void findAll() {
        PageRequest pageRequest =   PageRequest.of(0,10);

        Page<ProductInfo> all = productService.findAll(pageRequest);
        System.out.println( all.getTotalElements());
    }

    @Test
    void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123456789");
        productInfo.setCategoryType(1);
        productInfo.setProductDescription("xxxx");
        productInfo.setProductIcon("xxx");
        productInfo.setProductName("手剥巴丹木");
        productInfo.setProductPrice(new BigDecimal(100));
        productInfo.setProductStatus(1);
        productInfo.setProductStock(1000);
        ProductInfo save = productService.save(productInfo);
        Assert.notNull(save,"");
    }
}