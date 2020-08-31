package com.soton.shopping_centre.config;

import com.soton.shopping_centre.pojo.User;
import com.soton.shopping_centre.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

//Realm与数据库交互
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    //认证(login)
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /*String username = "root";
        String password = "root";*/

        UsernamePasswordToken userToken = (UsernamePasswordToken)authenticationToken;

        User user = userService.queryUserByName(userToken.getUsername());//query username
        if(user==null){
            return null; //no such username
        }
        else{
            //System.out.println(user);
            String password = user.getPassword(); //query password
            ByteSource bsSalt=  ByteSource.Util.bytes(user.getSalt());//query salt
            return new SimpleAuthenticationInfo(user,password,bsSalt,getName());
        }
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //System.out.println("authorization");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        info.addRole(user.getRoleName());
        return info;
    }
}