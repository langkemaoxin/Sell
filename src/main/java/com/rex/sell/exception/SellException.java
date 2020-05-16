package com.rex.sell.exception;

import com.rex.sell.enums.ResultEnum;

/**
 * @ClassName SellException
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 16:36
 * @Version 1.0
 */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
