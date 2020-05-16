package com.rex.sell.converter;

import com.rex.sell.dataobject.OrderMaster;
import com.rex.sell.dto.OrderDTO;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName OrderMaster2OrderDTOConvert
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/17 0:01
 * @Version 1.0
 */
public class OrderMaster2OrderDTOConvert {
    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasters){
        List<OrderDTO> orderDTOS = orderMasters.stream().map(o -> convert(o)).collect(Collectors.toList());
        return orderDTOS;
    }
}
