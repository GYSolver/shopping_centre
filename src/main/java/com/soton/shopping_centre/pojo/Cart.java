package com.soton.shopping_centre.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.soton.shopping_centre.service.ProductSpecificationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("cart")
public class Cart {
    @TableId(type= IdType.AUTO)
    private int id;
    private int productId;
    private int productSpecificationId;
    private int quantity;
    private int userId;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    private ProductSpecification productSpecification;
}
