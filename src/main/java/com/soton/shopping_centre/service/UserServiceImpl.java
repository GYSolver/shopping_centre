package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.UserMapper;
import com.soton.shopping_centre.pojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper usermapper;

    @Override
    public List<User> queryAllUsers() { return usermapper.selectList(null); }

    @Override
    public User queryUserById(Integer id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return usermapper.selectOne(queryWrapper);
    }

    @Override
    public User queryUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return usermapper.selectOne(queryWrapper);
    }

    @Override
    public User queryUserByRoleName(String roleName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name",roleName);
        return usermapper.selectOne(wrapper);
    }

    @Override
    public int addUser(User user) { return usermapper.insert(user); }

    @Override
    public int deleteUserById(Integer id) {
        return usermapper.deleteById(id);
    }

    @Override
    public int editUser(User user) {
        return usermapper.updateById(user);
    }

    @Override
    public int registerAdmin() {
        if(this.queryUserByRoleName("admin")==null){
            User user = new User();
            user.setUsername("admin");

            String salt = RandomStringUtils.randomNumeric(6);
            user.setSalt(salt);
            Md5Hash md5Hash = new Md5Hash("admin",salt); //encrypt
            user.setPassword(md5Hash.toString());

            user.setRoleName("admin");
            return this.addUser(user);
        }
        return 0;
    }

}
