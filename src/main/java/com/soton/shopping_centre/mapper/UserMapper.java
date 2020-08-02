package com.soton.shopping_centre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
   /* List<User> queryAllUsers();
    User queryUserById(Integer id);
    int addUser(User user);
    int deleteUserById(Integer id);
    int editUser(User user);*/
}
