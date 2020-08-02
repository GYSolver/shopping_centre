package com.soton.shopping_centre.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper extends BaseMapper<Product> {
    @Results(id="productMap",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "name",column = "name"),
            @Result(property = "brand",column = "brand"),
            @Result(property = "imagePath",column = "image_path"),
            @Result(property = "categoryId",column = "category_id"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "updateTime",column = "update_time"),
            @Result(property = "category",column = "category_id",
                    one = @One(select = "com.soton.shopping_centre.mapper.CategoryMapper.selectById"))
                 })
    /*@Select("select * from product left join category on product.category_id=category.id where product.id=#{id}")
    Product selectProductById(int id);*/

    @Select("select * from product left join category on product.category_id=category.id")
    //@ResultMap(value="productMap")
    List<Product> selectAllProducts();

    @Select("select * from product left join category on product.category_id=category.id where " + "${ew.sqlSegment}")
    //@ResultMap(value="productMap")
    Product selectOneProductBy(QueryWrapper<Product> wrapper);


}
