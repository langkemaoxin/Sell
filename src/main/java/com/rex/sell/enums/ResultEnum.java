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
    PARAM_ERROR(0,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存异常"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态异常"),
    ORDER_UPDATE_ERROR(15,"订单更新异常"),
    ORDER_PAY_STATUS_ERROR(16,"订单支付状态不正确"),
    CART_EMPTY(18,"购物车不能为空"),
    ;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}
