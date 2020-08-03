package com.soton.shopping_centre.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.UserMapper;
import com.soton.shopping_centre.pojo.User;
import com.soton.shopping_centre.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md2Hash;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    /*@GetMapping("/")
    public String onGetIndex(){ return "/front-stage/index"; }*/

    @GetMapping("/login")
    public String onGetLoginAdmin(){
        return "/front-stage/login";
    }

    @GetMapping("/logout")
    public String onGetLogout(){
        //current user
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }


}
