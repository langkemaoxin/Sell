package com.rex.sell.dataobject;

import com.rex.sell.enums.OrderStatusEnum;
import com.rex.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName OrderMaster
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/16 7:23
 * @Version 1.0
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();
    private Integer payStatus= PayStatusEnum.WAIT.getCode();
    private Date createTime;
    private Date updateTime;

}
