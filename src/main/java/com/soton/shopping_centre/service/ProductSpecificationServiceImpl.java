package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.ProductSpecificationMapper;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecificationServiceImpl implements ProductSpecificationService {
    @Autowired
    ProductSpecificationMapper productSpecificationMapper;

    @Override
    public List<ProductSpecification> queryAllPdctSpecs() {
        return  productSpecificationMapper.selectPdctSpecBy(null);
    }

    @Override
    public ProductSpecification queryPdctSpecById(Integer id) {
        QueryWrapper<ProductSpecification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_specification.id",id);
        return productSpecificationMapper.selectPdctSpecBy(queryWrapper).get(0);
    }

    @Override
    public List<ProductSpecification> queryPdctSpecByProductId(Integer productId) {
        QueryWrapper<ProductSpecification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_specification.product_id",productId);
        return productSpecificationMapper.selectPdctSpecBy(queryWrapper);
    }

    @Override
    public int addPdctSpec(ProductSpecification productSpecification) {
        return productSpecificationMapper.insert(productSpecification);
    }

    @Override
    public int deletePdctSpecById(Integer id) {
        return productSpecificationMapper.deleteById(id);
    }

    @Override
    public int editPdctSpec(ProductSpecification productSpecification) {
        return productSpecificationMapper.updateById(productSpecification);
    }
}
