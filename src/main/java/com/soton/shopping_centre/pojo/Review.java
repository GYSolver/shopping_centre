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
@TableName("`review`")
public class Review {
    @TableId(type= IdType.AUTO)
    private int id;
    private int orderId;
    private int userId;
    private String username;
    private int productId;
    private String productName;
    private String productSpecification;
    private String generalReview;
    private String detailedReview;
    private int rating;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    private List<OrderDetail> orderDetails;
}
