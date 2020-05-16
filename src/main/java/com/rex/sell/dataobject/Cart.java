package com.rex.sell.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName Cart
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 17:26
 * @Version 1.0
 */
@Data
public class Cart {
    private String productId;
    private Integer productQuantity;

    public Cart(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
