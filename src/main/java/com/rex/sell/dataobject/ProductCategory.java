package com.rex.sell.dataobject;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @ClassName ProductCategory
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/14 22:52
 * @Version 1.0
 */
@Entity
@Getter
@Setter
public class ProductCategory {
    @Id
    @GeneratedValue
    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}
