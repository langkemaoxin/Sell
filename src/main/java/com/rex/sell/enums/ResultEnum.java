package com.rex.sell.enums;

import lombok.Getter;

/**
 * @ClassName ResultEnum
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 16:37
 * @Version 1.0
 */
@Getter
public enum  ResultEnum {
    PRODUCT_NOT_EXIST(0,"商品不存在"),
    PRODUCT_STOCK_ERROR(1,"商品库存异常"),
    ;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}
