package com.soton.shopping_centre.controller.frontstage;

import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.pojo.Review;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.ProductService;
import com.soton.shopping_centre.service.ProductSpecificationService;
import com.soton.shopping_centre.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
//@RequestMapping("/")
public class FrontProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductSpecificationService productSpecificationService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/")
    public String onGetIndex(String[] selectedCategory,String range1,String range2, Integer categoryId, String searchText,Model model){
        List<Product> products = productService.queryAllProducts();
        List<String> categoryList = new ArrayList<>();
        if(selectedCategory!=null){
            categoryList = new ArrayList<>(Arrays.asList(selectedCategory));
            Iterator<Product> iterator = products.iterator();
            while(iterator.hasNext()){
                Product product = iterator.next();
                if(!categoryList.contains(product.getCategory().getName())){
                    iterator.remove();
                }
            }
        }
        if(range1!=null && range2!=null ){
            /*String a = range.substring(1,range.length());
            String left="",right="";
            for(int i=0;i<a.length();i++){
                if(a.charAt(i)==' '){
                    left=a.substring(0,i);
                    right=a.substring(i+4,a.length());
                    break;
                }
            }
            int l=Integer.parseInt(left),r=Integer.parseInt(right);*/
            if(range1.equals(""))
                range1="0";
            if(range2.equals(""))
                range2="0";
            int l=Integer.parseInt(range1),r=Integer.parseInt(range2);
            if(l<0) l=0;
            if(l>1000) l=1000;
            if(r<0) r=0;
            if(r>1000) r=1000;
            Iterator<Product> iterator = products.iterator();
            while(iterator.hasNext()){
                Product product = iterator.next();
                int price = product.getProductSpecifications().get(0).getPrice();
                if(price<l||price>r){
                    iterator.remove();
                }
            }
        }

        if(searchText!=null&&!searchText.equals("")){
            products = productService.queryProductsByCategoryIdAndProductName(categoryId, searchText);
        }

        model.addAttribute("products",products);
        List<Category> categories = categoryService.queryAllCategories();
        model.addAttribute("categories",categories);

        model.addAttribute("categoryList",categoryList);
        model.addAttribute("range1",range1);
        model.addAttribute("range2",range2);

        return "/front-stage/index";
    }

    /*@GetMapping("/search")
    public String onGetProductSearch(Integer categoryId, String searchText, Model model){
        List<Category> categories = categoryService.queryAllCategories();
        model.addAttribute("categories",categories);

        List<Product> products = productService.queryProductsByCategoryIdAndProductName(categoryId, searchText);
        model.addAttribute("products",products);
        return "/front-stage/index";
    }*/

    @GetMapping("/product-detail/{productId}")
    public String OnGetProductDetailIndex(@PathVariable Integer productId, Model model){
        Product product = productService.queryProductById(productId);
        model.addAttribute("product",product);

        List<ProductSpecification> productSpecifications = productSpecificationService.queryPdctSpecByProductId(productId);
        model.addAttribute("productSpecifications",productSpecifications);

        HashMap<String,Integer> pdctSpecMap = new LinkedHashMap<>();
        for(ProductSpecification productSpecification : productSpecifications){
            pdctSpecMap.put(productSpecification.getSpecification(),productSpecification.getStock());
        }
        model.addAttribute("pdctSpecMap",pdctSpecMap);

        List<Review> reviews = reviewService.queryReviewsByProductId(productId);
        model.addAttribute("reviews",reviews);

        return "/front-stage/product-detail";
    }
}
