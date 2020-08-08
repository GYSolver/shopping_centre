package com.soton.shopping_centre.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("user")
public class User {
    @TableId(type= IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private String salt;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String imagePath;
    private String address;
    private String postcode;
    private String roleId;
    private String roleName;
    private String createTime;
    private String updateTime;
}
