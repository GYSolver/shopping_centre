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
@TableName(value = "product")//, resultMap = "productMap")
public class Product {
    @TableId(type= IdType.AUTO)
    private int id;
    private String name;
    private String brand;
    private int categoryId;
    private String image_path;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    private Category category;
    @TableField(exist = false)
    private List<ProductSpecification> productSpecifications;
}
