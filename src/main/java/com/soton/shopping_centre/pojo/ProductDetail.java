package com.soton.shopping_centre.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("product_detail")
public class ProductDetail {
    private int id;
    private int productId;
    private String name;
    private int price;
    private String imagePath;
    private String createTime;
    private String updateTime;
}
