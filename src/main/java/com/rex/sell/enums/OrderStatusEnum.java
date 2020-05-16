package com.rex.sell.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @ClassName OrderStatusEnum
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 7:27
 * @Version 1.0
 */
@Getter
public enum  OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消");

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}
