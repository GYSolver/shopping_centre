package com.soton.shopping_centre.controller.dashboard;

import com.soton.shopping_centre.pojo.Order;
import com.soton.shopping_centre.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard/order")
public class OrderManagementController {
    @Autowired
    OrderService orderService;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Order> orders = orderService.queryAllOrders();
        model.addAttribute("orders",orders);
        return "/dashboard/order/index";
    }
}
