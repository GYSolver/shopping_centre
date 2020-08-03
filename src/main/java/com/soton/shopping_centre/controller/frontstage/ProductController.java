package com.soton.shopping_centre.controller.frontstage;

import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller("front-stage-product")
//@RequestMapping("/")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Product> products = productService.queryAllProducts();
        //List<Category> categories = categoryService.queryAllCategories();

        model.addAttribute("products",products);
        //model.addAttribute("categories",categories);

        return "/front-stage/index";
    }
    @GetMapping("/product-detail/{id}")
    public String onGetProductDetail(@PathVariable Integer id,Model model){
        Product product = productService.queryProductById(id);
        //List<Category> categories = categoryService.queryAllCategories();

        model.addAttribute("productDetail",product);
        //model.addAttribute("categories",categories);

        return "/front-stage/product-detail";
    }
}
