package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.User;

import java.util.List;

public interface UserService {

    public List<User> queryAllUsers();
    public User queryUserById(Integer id);
    public User queryUserByName(String username);
    public User queryUserByRoleName(String roleName);

    public int addUser(User user);
    public int deleteUserById(Integer id);
    public int editUser(User user);

    public int registerAdmin();

}
