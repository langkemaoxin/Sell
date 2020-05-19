package com.rex.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rex.sell.dataobject.OrderDetail;
import com.rex.sell.enums.OrderStatusEnum;
import com.rex.sell.enums.PayStatusEnum;
import com.rex.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderDTO
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 16:16
 * @Version 1.0
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Integer payStatus;

    //@JsonSerialize(using = Date2LongSerializer.class )
    private Date createTime;
    private Date updateTime;
    private List<OrderDetail> orderDetailList;
}
