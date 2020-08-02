package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.ProductMapper;
import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> queryAllProducts() {
        //return productMapper.selectList(null);
        return productMapper.selectAllProducts();
    }

    @Override
    public Product queryProductById(Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        //return productMapper.selectOne(queryWrapper);
        return productMapper.selectOneProductBy(queryWrapper);
    }

    @Override
    public Product queryProductByName(String name) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        //return productMapper.selectOne(queryWrapper);
        return productMapper.selectOneProductBy(queryWrapper);
    }

    @Override
    public int addProduct(Product product) { return productMapper.insert(product); }

    @Override
    public int deleteProductById(Integer id) { return productMapper.deleteById(id); }

    @Override
    public int editProduct(Product product) { return productMapper.updateById(product); }
}
