package com.soton.shopping_centre.controller.frontstage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    ObjectMapper objectMapper;

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
        return "/front-stage/login";
    }

    @GetMapping("/login")
    public String onGetLogin(){
        return "/front-stage/login";
    }

    @PostMapping("/login")
    public String onPostLogin(String username, String password, String rememberMe, Model model){
        //get current user
        Subject subject = SecurityUtils.getSubject();
        //package token
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        if(rememberMe!=null)
            token.setRememberMe(rememberMe.equals("on"));
        try {
            subject.login(token); //login
            User user = (User) subject.getPrincipal();
            if(user.getRoleName().equals("admin"))
                return "redirect:/dashboard/stock/";
            if(user.getRoleName().equals("member"))
                return "redirect:/";

            model.addAttribute("err","No such role");
            return "/front-stage/login";
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

    @GetMapping("/my-account")
    public String onGetMyAccount(Model model) throws JsonProcessingException {
        //get current user
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        model.addAttribute("user",user);

        String addrDb = user.getAddress();
        String[] addr;
        if(!addrDb.equals(""))
            addr = objectMapper.readValue(addrDb, String[].class);
        else
            addr = new String[6];

        model.addAttribute("addr",addr);
        return "/front-stage/my-account";
    }

    @PostMapping("/edit-profile")
    public String onPostProfile(User user,String[] addr) throws JsonProcessingException {
        user.setAddress(objectMapper.writeValueAsString(addr));
        userService.editUser(user);

        Subject subject = SecurityUtils.getSubject();
        User userShiro = (User) subject.getPrincipal();

        userShiro.setFirstName(user.getFirstName());
        userShiro.setLastName(user.getLastName());
        userShiro.setAddress(user.getAddress());
        return "redirect:/my-account";
    }

    @PostMapping("/edit-pwd")
    public String onPostPwd(User user,String password,String passwordConfirm,Model model){
        Subject subject = SecurityUtils.getSubject();
        User userShiro = (User) subject.getPrincipal();

        if(!password.equals("")){
            if(password.equals(passwordConfirm)){
                String salt = RandomStringUtils.randomNumeric(6);
                user.setSalt(salt);
                userShiro.setSalt(salt);
                Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt); //encrypt
                user.setPassword(md5Hash.toString());
                userShiro.setPassword(md5Hash.toString());
            }
            else {
                model.addAttribute("err","Please enter the same password");
                return "/front-stage/my-account";
            }
        }
        userService.editUser(user);
        return "redirect:/my-account";
    }

    @GetMapping("/unauthorized")
    public String onGetUnauthorizedPage(){return "/front-stage/unauthorized";}
}
