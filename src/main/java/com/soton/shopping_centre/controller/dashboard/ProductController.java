package com.soton.shopping_centre.controller.dashboard;

import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard/product")
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

        return "/dashboard/product/index";
    }

    @GetMapping("/add")
    public String onGetAdd(Model model){
        List<Category> categories = categoryService.queryAllCategories();
        model.addAttribute("categories",categories);
        return "/dashboard/product/add";
    }

    @PostMapping("/add")
    public String onPostAdd(Product product){
        if(product.getName()!=null){
            productService.addProduct(product);
            return "redirect:/dashboard/product/";
        }
        else
            return "/front-stage/404";
    }

    @GetMapping("/edit/{id}")
    public String onGetEdit(@PathVariable Integer id, Model model){
        //category
        Product product = productService.queryProductById(id);
        model.addAttribute("product",product);
        return "/dashboard/product/edit";
    }

    @PostMapping("/edit")
    public String onPostEdit(Product product){
        if(product.getName()!=null){
            productService.editProduct(product);
            return "redirect:/dashboard/product/";
        }
        else
            return "/front-stage/404";
    }


    @PostMapping("/delete/{id}")
    public String onPostDelete(@PathVariable Integer id){
        productService.deleteProductById(id);
        return "redirect:/dashboard/product/";
    }
}
