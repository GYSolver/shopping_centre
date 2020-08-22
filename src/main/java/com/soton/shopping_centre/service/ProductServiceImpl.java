package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.ProductMapper;
import com.soton.shopping_centre.mapper.ProductSpecificationMapper;
import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductSpecificationService productSpecificationService;

    @Override
    public List<Product> queryAllProducts() {
        //return productMapper.selectList(null);
        return productMapper.selectAllProducts();
    }

    @Override
    public Product queryProductById(Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product.id",id);
        //return productMapper.selectOne(queryWrapper);
        return productMapper.selectOneProductBy(queryWrapper);
    }

    @Override
    public Product queryProductByName(String name) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product.name",name);
        //return productMapper.selectOne(queryWrapper);
        return productMapper.selectOneProductBy(queryWrapper);
    }

    @Override
    public List<Product> queryProductsByCategoryId(Integer cid) {
        return productMapper.selectProductsByCategoryId(cid);
    }

    @Override
    public List<Product> queryProductsByCategoryIdAndProductName(Integer cid, String pName) {
        /*QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("product.name",pName);
        if(cid!=0){
            queryWrapper.eq("product.categoryId",cid);
        }*/
        if(cid==0){
            return productMapper.selectProductsByProductName(pName);
        }
        else{
            return productMapper.selectProductsByCategoryIdAndProductName(cid,pName);
        }
    }

    @Override
    public int addProduct(Product product) { return productMapper.insert(product); }

    @Override
    public int deleteProductById(Integer id) {
        List<ProductSpecification> productSpecifications = productSpecificationService.queryPdctSpecByProductId(id);
        for (ProductSpecification productSpecification : productSpecifications) {
            productSpecificationService.deletePdctSpecById(productSpecification.getId());
        }
        return productMapper.deleteById(id);
    }

    @Override
    public int editProduct(Product product) { return productMapper.updateById(product); }
}
