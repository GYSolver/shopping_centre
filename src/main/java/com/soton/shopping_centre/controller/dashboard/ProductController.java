package com.soton.shopping_centre.controller.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.pojo.Specification;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.ProductService;
import com.soton.shopping_centre.service.ProductSpecificationService;
import com.soton.shopping_centre.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/dashboard/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductSpecificationService productSpecificationService;

    @Autowired
    SpecificationService specificationService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Product> products = productService.queryAllProducts();
        model.addAttribute("products",products);
        return "/dashboard/product/index";
    }

    @GetMapping("/add")
    public String onGetAdd(Model model){
        List<Category> categories = categoryService.queryAllCategories();
        model.addAttribute("categories",categories);
        return "/dashboard/product/add";
    }

    @PostMapping("/add")
    public String onPostAdd(Product product,int[] specificationId,int[] price,int[] stock) throws JsonProcessingException {
        if(product!=null&&product.getName()!=null&&price!=null&&stock!=null){
            productService.addProduct(product);
            String jsonStr=null;
            //serialize specifications
            for(int i=0;i<price.length;i++){
                if(specificationId!=null) {
                    HashMap<String,String> specMap = new HashMap<>();
                    for (int j = 0; j < specificationId.length / price.length; j++) {
                        Specification specification = specificationService.querySpecificationById(specificationId[i * price.length + j]);
                        specMap.put(specification.getName(), specification.getValue());
                    }
                    jsonStr = objectMapper.writeValueAsString(specMap);
                }
                ProductSpecification productSpecification=new ProductSpecification();
                productSpecification.setProductId(product.getId());
                productSpecification.setSpecification(jsonStr);
                productSpecification.setPrice(price[i]);
                productSpecification.setStock(stock[i]);
                productSpecificationService.addPdctSpec(productSpecification);
            }
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
        if(product!=null&&product.getName()!=null){
            productService.editProduct(product);
            return "redirect:/dashboard/product/";
        }
        else
            return "/front-stage/404";
    }


    @PostMapping("/delete/{id}")
    public String onPostDelete(@PathVariable Integer id){
        productService.deleteProductById(id);
        //productSpecifications are also deleted in ProductService
        return "redirect:/dashboard/product/";
    }
}
