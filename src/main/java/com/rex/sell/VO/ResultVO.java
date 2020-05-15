package com.rex.sell.VO;

import lombok.Data;

/**
 * @ClassName ResultVO
 * @Description HTTP请求返回的最外层对象
 * @Author GY.C
 * @Date 2020/5/15 22:05
 * @Version 1.0
 */
@Data
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T data;
}
