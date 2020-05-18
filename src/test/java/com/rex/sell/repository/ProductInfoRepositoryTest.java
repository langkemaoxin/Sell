package com.rex.sell.repository;

import com.rex.sell.dataobject.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    void  saveTest(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("1234545221");
        productInfo.setCategoryType(1);
        productInfo.setProductDescription("xxxx");
        productInfo.setProductIcon("xxx");
        productInfo.setProductName("牙膏");
        productInfo.setProductPrice(new BigDecimal(100));
        productInfo.setProductStatus(1);
        productInfo.setProductStock(1000);

        ProductInfo save = repository.save(productInfo);
        Assert.notNull(save,"");

    }

    @Test
    void findByProductStatus() {
        List<ProductInfo> byProductStatus = repository.findByProductStatus(1);
        Assert.isTrue(byProductStatus.size()!=0,"");
    }
}