package com.rex.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rex.sell.dataobject.OrderDetail;
import com.rex.sell.dto.OrderDTO;
import com.rex.sell.enums.ResultEnum;
import com.rex.sell.exception.SellException;
import com.rex.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OrderForm2OrderDTOConverter
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/17 18:12
 * @Version 1.0
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetails=new ArrayList<>();

        try {
            orderDetails = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        }catch (Exception ex){
            log.error("【对象转换错误】string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetails);

        return orderDTO;
    }
}
