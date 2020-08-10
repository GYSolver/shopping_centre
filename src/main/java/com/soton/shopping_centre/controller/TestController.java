package com.soton.shopping_centre.controller;

import com.soton.shopping_centre.mapper.ProductMapper;
import com.soton.shopping_centre.mapper.UserMapper;
import com.soton.shopping_centre.pojo.*;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductMapper productMapper;
    @Autowired
    SpecificationService specificationService;

    @RequestMapping("/test")
    public List<User> userList(){
        return userMapper.selectList(null);
    }

    @RequestMapping("/test2")
    public List<Category> CategoryList(){
        return categoryService.queryAllCategories();
    }

    /*@RequestMapping("/test3")
    public Product Product(){
        return productMapper.selectProductById(1);
    }*/

    @RequestMapping("/test4")
    public List<Product> Product2(){
        return productMapper.selectAllProducts();
    }


}
