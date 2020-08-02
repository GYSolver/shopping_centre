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
@TableName("category")
public class Category {
    private int id;
    private String name;
    private String imagePath;
    private String createTime;
    private String updateTime;
}
