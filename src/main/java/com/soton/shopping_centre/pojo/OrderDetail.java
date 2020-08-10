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
@TableName("order_detail")
public class OrderDetail {
    @TableId(type= IdType.AUTO)
    private int id;
    private int orderId;
    private int productId;
    private String productName;
    private String productSpecification;
    private int price;
    private int quantity;
    private String createTime;
    private String updateTime;
}
