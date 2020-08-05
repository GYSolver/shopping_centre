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
@TableName("category")
public class Category {
    @TableId(type= IdType.AUTO)
    private int id;
    private String name;
    private String imagePath;
    private String createTime;
    private String updateTime;

    @TableField(exist = false)
    List<Specification> specifications;
}
