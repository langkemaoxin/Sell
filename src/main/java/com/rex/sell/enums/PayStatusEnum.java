package com.rex.sell.enums;

import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * @ClassName PayStatusEnum
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 7:29
 * @Version 1.0
 */
@Getter
public enum  PayStatusEnum {
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功")
    ;
    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
