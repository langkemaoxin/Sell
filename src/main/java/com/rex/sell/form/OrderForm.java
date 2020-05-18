package com.rex.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @ClassName OrderForm
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/17 18:03
 * @Version 1.0
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "openId必填")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;
}
