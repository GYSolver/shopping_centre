package com.soton.shopping_centre.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("specification")
public class Specification {
    @TableId(type= IdType.AUTO)
    private int id;
    private int categoryId;
    private String name;
    private String value;
    private String createTime;
    private String updateTime;
}
