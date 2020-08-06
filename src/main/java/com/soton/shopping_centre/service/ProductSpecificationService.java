package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;

import java.util.List;

public interface ProductSpecificationService {
    public List<ProductSpecification> queryAllPdctSpecs();
    public ProductSpecification queryPdctSpecById(Integer id);
    public List<ProductSpecification> queryPdctSpecByProductId(Integer productId);
    //public ProductSpecification queryPdctSpecBySpec(String spec);

    public int addPdctSpec(ProductSpecification productSpecification);
    public int deletePdctSpecById(Integer id);
    public int editPdctSpec(ProductSpecification productSpecification);
}
