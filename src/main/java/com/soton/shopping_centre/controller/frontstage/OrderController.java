package com.soton.shopping_centre.controller.frontstage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soton.shopping_centre.pojo.*;
import com.soton.shopping_centre.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    ProductSpecificationService productSpecificationService;
    @Autowired
    ObjectMapper objectMapper;


    @GetMapping("/checkout")
    public String onGetCheckout(Model model) throws JsonProcessingException {
        //get current user
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user==null){
            return "redirect:/login";
        }
        model.addAttribute("user",user);

        //get addr
        String addrDb = user.getAddress();
        String[] addr;
        if(!addrDb.equals(""))
            addr = objectMapper.readValue(addrDb, String[].class);
        else
            addr = new String[5];
        model.addAttribute("addr",addr);

        List<Cart> carts = cartService.queryCartsByUserId(user.getId());
        if(carts.size()==0){
            model.addAttribute("err","Please add products into cart!");
            return "/front-stage/cart";
        }
        //check stock
        boolean hasErr=false;
        Map<String,String> errMap=new HashMap<>();
        for(Cart cart:carts){
            ProductSpecification pdctSpecDb = productSpecificationService.queryPdctSpecById(cart.getProductSpecificationId());
            ProductSpecification pdctSpecCart = cart.getProductSpecification();
            if(pdctSpecDb==null){
                errMap.put("err", pdctSpecCart.getProduct().getName()+pdctSpecCart.getSpecification()+"is unavailable, please reselect.");
                hasErr=true;
            }
            else if(cart.getQuantity()>pdctSpecDb.getStock()){
                errMap.put("err",pdctSpecCart.getProduct().getName()+pdctSpecCart.getSpecification()+"is out of stock, please reselect.");
                hasErr=true;
            }
        }
        if(hasErr){
            model.addAllAttributes(errMap);
            return "/front-stage/cart";
        }
        else {
            model.addAttribute("carts", carts);
            return "/front-stage/checkout";
        }
    }

    @PostMapping("/checkout")
    public String onPostCheckout(String[] addr,User user,Model model) throws JsonProcessingException {
        user.setAddress(objectMapper.writeValueAsString(addr));
        userService.editUser(user);
        model.addAttribute("user",user);
        List<Cart> carts = cartService.queryCartsByUserId(user.getId());
        model.addAttribute("carts",carts);
        return "/front-stage/payment";
    }

    @PostMapping("/payment")
    public String onPostPayment(String[] card,Integer userId, Model model) throws JsonProcessingException {
        if(card.length>=2){
            User user = userService.queryUserById(userId);
            List<Cart> carts = cartService.queryCartsByUserId(userId);
            model.addAttribute("carts",carts);

            Order order = new Order();
            orderService.addOrder(order);
            int orderId = order.getId();
            int totalPrice=0,itemsCount=0;
            for(Cart cart:carts){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setProductId(cart.getProductId());
                orderDetail.setProductName(cart.getProductSpecification().getProduct().getName());
                orderDetail.setProductSpecification(cart.getProductSpecification().getSpecification());
                orderDetail.setQuantity(cart.getQuantity());
                orderDetail.setPrice(cart.getProductSpecification().getPrice());
                orderDetailService.addOrderDetail(orderDetail);

                totalPrice+=(orderDetail.getQuantity()*orderDetail.getPrice());
                itemsCount+=cart.getQuantity();

                //clear cart
                cartService.deleteCartById(cart.getId());

                //update stock
                ProductSpecification productSpecification = cart.getProductSpecification();
                int stock = productSpecification.getStock();
                productSpecification.setStock(stock - orderDetail.getQuantity());
                productSpecificationService.editPdctSpec(productSpecification);
            }
            order.setItemsCount(itemsCount);
            order.setTotalPrice(totalPrice);
            order.setStatus("paid");
            order.setPaymentInfo(objectMapper.writeValueAsString(card));
            order.setAddress(user.getAddress());
            order.setPostcode(user.getPostcode());
            order.setFirstName(user.getFirstName());
            order.setLastName(user.getLastName());
            order.setEmail(user.getEmail());
            order.setPhoneNumber(user.getPhoneNumber());
            order.setUserId(user.getId());
            order.setUsername(user.getUsername());
            orderService.editOrder(order);
            return "redirect:/my-orders";
        }
        else{
            model.addAttribute("err","Please check your payment information.");
            return "/front-stage/payment";
        }
    }

    @GetMapping("/my-orders")
    public String onGetAllOrders(Model model){
        //get current user
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user!=null){
            List<Order> orders = orderService.queryOrdersByUserId(user.getId());
            model.addAttribute("orders",orders);
            return "/front-stage/my-orders";
        }
        else
            return "/front-stage/unauthorized";

    }

    @PostMapping("/delete-order/{id}")
    public String onPostDeleteOrder(@PathVariable Integer id,Model model){
        //get current user
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user!=null){
            orderService.deleteOrderById(id);
            return "redirect:/my-orders";
        }
        else
            return "/front-stage/unauthorized";

    }


}
