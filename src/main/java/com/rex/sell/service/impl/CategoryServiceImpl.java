package com.rex.sell.service.impl;

import com.rex.sell.dataobject.ProductCategory;
import com.rex.sell.repository.ProductCategoryRepository;
import com.rex.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author GY.C
 * @Date 2020/5/15 8:16
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        Optional<ProductCategory> byId = repository.findById(categoryId);
        ProductCategory productCategory = byId.get();
        return productCategory;
    }

    @Override
    public List<ProductCategory>  findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
