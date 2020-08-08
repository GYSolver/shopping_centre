package com.soton.shopping_centre.controller.frontstage;

import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.ProductService;
import com.soton.shopping_centre.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping("/")
public class FrontProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductSpecificationService productSpecificationService;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Product> products = productService.queryAllProducts();
        model.addAttribute("products",products);

        return "/front-stage/index";
    }

    @GetMapping("/product-detail/{productId}")
    public String OnGetProductDetailIndex(@PathVariable Integer productId, Model model){
        Product product = productService.queryProductById(productId);
        model.addAttribute("product",product);

        List<ProductSpecification> productSpecifications = productSpecificationService.queryPdctSpecByProductId(productId);
        model.addAttribute("productSpecifications",productSpecifications);

        HashMap<String,Integer> pdctSpecMap = new HashMap<>();
        for(ProductSpecification productSpecification : productSpecifications){
            pdctSpecMap.put(productSpecification.getSpecification(),productSpecification.getStock());
        }
        model.addAttribute("pdctSpecMap",pdctSpecMap);

        return "/front-stage/product-detail";
    }
}
