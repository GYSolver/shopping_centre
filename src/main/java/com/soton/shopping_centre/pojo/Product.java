package com.soton.shopping_centre.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "product")//, resultMap = "productMap")
public class Product {
    private int id;
    private String name;
    private String brand;
    private String imagePath;
    private int categoryId;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    private Category category;
}
