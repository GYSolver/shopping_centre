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
@TableName("product_specification")
public class ProductSpecification {
    @TableId(type= IdType.AUTO)
    private int id;
    private int productId;
    private String specification;
    private int price;
    private int stock;
    private String imagePath;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    private Product product;

}
