package com.soton.shopping_centre.controller.dashboard;

import com.soton.shopping_centre.pojo.User;
import com.soton.shopping_centre.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String onGetDashboard(){ return "/dashboard/index"; }

    @PostMapping("/register-admin")
    public String onPostRegisterAdmin(){
        userService.registerAdmin();
        return "dashboard/login";
    }

    @GetMapping("/login-admin")
    public String onGetLoginAdmin(){
        return "/dashboard/login";
    }

    @PostMapping("/login-admin")
    public String onPostLoginAdmin(String username, String password, Model model){
        User user = userService.queryUserByName(username);
        if(!user.getRoleName().equals("admin")){
            model.addAttribute("msg","Only admin can login.");
            return "/dashboard/login";
        }
        //get current user
        Subject subject = SecurityUtils.getSubject();
        //package token
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try {
            subject.login(token); //login
            return "redirect:/dashboard/";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","Username not found");
            e.printStackTrace();
            return "/dashboard/login";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("msg","Wrong password");
            e.printStackTrace();
            return "/dashboard/login";
        }
    }

    @GetMapping("/logout")
    public String onGetLogout(){
        //current user
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/dashboard/login-admin";
    }
}
