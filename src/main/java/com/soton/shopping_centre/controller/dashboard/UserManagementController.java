package com.soton.shopping_centre.controller.dashboard;

import com.soton.shopping_centre.pojo.Order;
import com.soton.shopping_centre.pojo.User;
import com.soton.shopping_centre.service.OrderService;
import com.soton.shopping_centre.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard/user")
public class UserManagementController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<User> users = userService.queryAllUsers();
        model.addAttribute("users",users);
        return "/dashboard/user/index";
    }
}
