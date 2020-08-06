package com.soton.shopping_centre.controller.frontstage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soton.shopping_centre.pojo.Cart;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.pojo.Specification;
import com.soton.shopping_centre.pojo.User;
import com.soton.shopping_centre.service.CartService;
import com.soton.shopping_centre.service.ProductSpecificationService;
import com.soton.shopping_centre.service.SpecificationService;
import com.sun.deploy.net.URLEncoder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    public String OnGetIndex(Model model, HttpServletRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        //has logged in: query from db; else: query from cookie;
        Subject currentUser = SecurityUtils.getSubject();
        List<Cart> carts;
        if (currentUser.isAuthenticated()){
           carts = cartService.queryAllCarts();
        }
        else{
            carts = new ArrayList<>();
            Cookie[] cookies =  request.getCookies();
            if(cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().substring(0,8).equals("cartData")) {
                        String cookieStr = URLDecoder.decode(cookie.getValue(),"utf-8");
                        HashMap<String, String> cartMap = objectMapper.readValue(cookieStr, HashMap.class);

                        Cart cart = new Cart();
                        cart.setProductId(Integer.parseInt(cartMap.get("productId")));
                        cart.setProductSpecificationId(Integer.parseInt(cartMap.get("productSpecificationId")));
                        cart.setQuantity(Integer.parseInt(cartMap.get("quantity")));
                        ProductSpecification productSpecification = objectMapper.readValue(cartMap.get("pdctSpec"), ProductSpecification.class);
                        cart.setProductSpecification(productSpecification);

                        carts.add(cart);
                    }
                }
            }
        }
        model.addAttribute("carts",carts);
        return "/front-stage/cart";
    }

    @PostMapping("/add")
    public String OnPostAdd(Integer productId, int[] specificationId, Integer quantity, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        if (productId != null) {
            //get product specification
            ProductSpecification productSpecification = null;
            int productSpecificationId = 0;
            String jsonStr = null;
            if (specificationId.length > 0) {
                HashMap<String, String> specMap = new HashMap<>();
                for (int value : specificationId) {
                    Specification specification = specificationService.querySpecificationById(value);
                    specMap.put(specification.getName(), specification.getValue());
                }
                jsonStr = objectMapper.writeValueAsString(specMap);

                List<ProductSpecification> productSpecifications = productSpecificationService.queryPdctSpecByProductId(productId);
                Map<String, Integer> specifications = new HashMap<>();
                for (ProductSpecification ps : productSpecifications) {
                    specifications.put(ps.getSpecification(), ps.getId());
                }
                //productSpecificationService.queryPdctSpecBySpec(); //can be implemented by querying database. Need to implement mapper and service.
                if (specifications.containsKey(jsonStr)) {
                    productSpecificationId = specifications.get(jsonStr);
                    productSpecification = productSpecificationService.queryPdctSpecById(productSpecificationId);
                }
            }
            else {
                productSpecification = productSpecificationService.queryPdctSpecByProductId(productId).get(0);
                productSpecificationId = productSpecification.getId();
            }

            //has logged in: storage in db; else: storage in cookie;
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser.isAuthenticated()) {

                Cart cart = new Cart();
                cart.setProductId(productId);
                cart.setQuantity(quantity);

                User user = (User) currentUser.getPrincipal();
                cart.setUserId(user.getId());

                cart.setProductSpecificationId(productSpecificationId);
                cart.setProductSpecification(productSpecification);
                cartService.addCart(cart);

            }
            else {
                String pdctSpecStr = objectMapper.writeValueAsString(productSpecification);

                HashMap<String, String> cartMap = new HashMap<>();
                cartMap.put("productId", String.valueOf(productId));
                cartMap.put("productSpecificationId", String.valueOf(productSpecificationId));
                cartMap.put("quantity", String.valueOf(quantity));
                cartMap.put("pdctSpec",pdctSpecStr);

                String cookieStr= URLEncoder.encode(objectMapper.writeValueAsString(cartMap),"utf-8") ;
                String cookieName="cartData"+System.currentTimeMillis();
                Cookie cartCookie = new Cookie(cookieName, cookieStr);

                cartCookie.setMaxAge(24 * 60 * 60);// 24h
                cartCookie.setPath("/");
                response.addCookie(cartCookie);
            }

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
