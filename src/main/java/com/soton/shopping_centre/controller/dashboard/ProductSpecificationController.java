package com.soton.shopping_centre.controller.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import java.util.List;
import java.util.Map;

@RequestMapping("/dashboard/pdctSpec")
@Controller
public class ProductSpecificationController {
    @Autowired
    ProductSpecificationService productSpecificationService;
    @Autowired
    SpecificationService specificationService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Autowired
    ObjectMapper objectMapper;


    @GetMapping("/add/{pId}")
    public String onGetAdd(@PathVariable Integer pId,Model model){
        Product product = productService.queryProductById(pId);
        model.addAttribute("product",product);

        Category category = categoryService.queryCategoryById(product.getCategoryId());
        model.addAttribute("category",category);
        return "/dashboard/product/PdctSpec/add";
    }

    @PostMapping("/add")
    public String onPostAdd(ProductSpecification productSpecification, Integer[] specificationId) throws JsonProcessingException {
        Product product = productService.queryProductById(productSpecification.getProductId());
        if(product!=null){
            //serialize specifications
            String jsonStr=null;
            if(specificationId!=null){
                HashMap<String,String> specMap = new HashMap<>();
                for(int i=0;i<specificationId.length;i++){
                    Specification specification = specificationService.querySpecificationById(specificationId[i]);
                    specMap.put(specification.getName(),specification.getValue());
                }
                jsonStr = objectMapper.writeValueAsString(specMap);
            }
            productSpecification.setSpecification(jsonStr);
            productSpecificationService.addPdctSpec(productSpecification);

            return "redirect:/dashboard/product/";
        }
        else
            return "/front-stage/404";
    }

    @PostMapping("/delete/{id}")
    public String onPostDelete(@PathVariable Integer id){
        productSpecificationService.deletePdctSpecById(id);
        return "redirect:/dashboard/product/";
    }

    @GetMapping("/edit/{id}")
    public String onGetEdit(@PathVariable Integer id, Model model) throws JsonProcessingException {

        ProductSpecification productSpecification = productSpecificationService.queryPdctSpecById(id);
        model.addAttribute("productSpecification",productSpecification);

        String specification = productSpecification.getSpecification();
        HashMap<String,String> specMap = specification.equals("") ?null:objectMapper.readValue(specification, HashMap.class);
        model.addAttribute("specMap",specMap);

        Category category = productService.queryProductById(productSpecification.getProductId()).getCategory();
        model.addAttribute("category",category);

        List<Specification> specifications = specificationService.querySpecificationsByCategoryId(category.getId());
        model.addAttribute("specifications",specifications);

        return "/dashboard/product/PdctSpec/edit";
    }

    @PostMapping("/edit")
    public String onPostEdit(ProductSpecification productSpecification,Integer[] specificationId) throws JsonProcessingException {
        if(productSpecification!=null){
            String jsonStr=null;
            if(specificationId!=null){
                HashMap<String,String> specMap = new HashMap<>();
                for(int i=0;i<specificationId.length;i++){
                    Specification specification = specificationService.querySpecificationById(specificationId[i]);
                    specMap.put(specification.getName(),specification.getValue());
                }
                jsonStr = objectMapper.writeValueAsString(specMap);
            }

            productSpecification.setSpecification(jsonStr);
            productSpecificationService.editPdctSpec(productSpecification);
            return "redirect:/dashboard/product/";

        }
        else
            return "/front-stage/404";
    }
}
