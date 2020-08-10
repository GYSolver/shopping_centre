package com.soton.shopping_centre.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("`order`")
public class Order {
    @TableId(type= IdType.AUTO)
    private int id;
    private int itemsCount;
    private int totalPrice;
    private String status;
    private String paymentInfo;
    private String address;
    private String postcode;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int userId;
    private String username;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    private List<OrderDetail> orderDetails;

}
