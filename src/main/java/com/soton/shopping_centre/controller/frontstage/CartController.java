package com.soton.shopping_centre.controller.frontstage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soton.shopping_centre.pojo.Cart;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.pojo.Specification;
import com.soton.shopping_centre.service.CartService;
import com.soton.shopping_centre.service.ProductSpecificationService;
import com.soton.shopping_centre.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    SpecificationService specificationService;
    @Autowired
    ProductSpecificationService productSpecificationService;
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/")
    public String OnGetIndex(Model model){
        List<Cart> carts = cartService.queryAllCarts();
        model.addAttribute("carts",carts);
        return "/front-stage/cart";
    }

    @PostMapping("/add")
    public String OnPostAdd(Integer productId, int[] specificationId,Integer quantity) throws JsonProcessingException {
        if(productId!=null){
            Cart cart = new Cart();
            cart.setProductId(productId);
            cart.setQuantity(quantity);

            String jsonStr=null;
            if(specificationId.length>0){
                HashMap<String,String> specMap = new HashMap<>();
                for(int i=0;i<specificationId.length;i++){
                    Specification specification = specificationService.querySpecificationById(specificationId[i]);
                    specMap.put(specification.getName(),specification.getValue());
                }
                jsonStr = objectMapper.writeValueAsString(specMap);

                List<ProductSpecification> productSpecifications = productSpecificationService.queryPdctSpecByProductId(productId);
                Map<String,Integer> specifications = new HashMap<>();
                for(ProductSpecification productSpecification:productSpecifications){
                    specifications.put(productSpecification.getSpecification(),productSpecification.getId());
                }
                if(specifications.containsKey(jsonStr)){
                    cart.setProductSpecificationId(specifications.get(jsonStr));
                    cart.setProductSpecification(productSpecificationService.queryPdctSpecById(specifications.get(jsonStr)));
                }
            }
            else{
                ProductSpecification productSpecification = productSpecificationService.queryPdctSpecByProductId(productId).get(0);
                cart.setProductSpecificationId(productSpecification.getId());
                cart.setProductSpecification(productSpecification);
            }

            cartService.addCart(cart);
            return "redirect:/cart/";
        }
        else
            return "/front-stage/404";
    }

    @PostMapping("/delete/{id}")
    public  String OnPostDelete(@PathVariable Integer id){
        cartService.deleteCartById(id);
        return "redirect:/cart/";
    }
}
