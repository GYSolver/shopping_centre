package com.soton.shopping_centre.controller.frontstage;

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

    @GetMapping("/register")
    public String onGetRegisterMember(){return "/front-stage/register";}

    @PostMapping("/register")
    public String onPostRegisterMember(User user,Model model){
        if(user==null||user.getUsername()==null||user.getPassword()==null){
            model.addAttribute("err","Please enter your information.");
            return "/front-stage/register";
        }
        int res = userService.registerMember(user);
        if(res==-1){
            model.addAttribute("err","Username has been registered.");
            return "/front-stage/register";
        }
        model.addAttribute("err","Username has been registered.");
        return "/front-stage/login";
    }

    @GetMapping("/login")
    public String onGetLoginMember(){
        return "/front-stage/login";
    }

    @PostMapping("/login")
    public String onPostLoginMember(String username, String password, Model model){
        //get current user
        Subject subject = SecurityUtils.getSubject();
        //package token
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);

        try {
            subject.login(token); //login
            return "redirect:/";
        } catch (UnknownAccountException e) {
            model.addAttribute("err","Username not found.");
            e.printStackTrace();
            return "/front-stage/login";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("err","Wrong password");
            e.printStackTrace();
            return "/front-stage/login";
        }
    }

    @GetMapping("/logout")
    public String onGetLogout(){
        //current user
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }


}
