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
            @Result(property = "categoryId",column = "category_id"),
            @Result(property = "imagePath",column = "image_path"),
            @Result(property = "detail",column = "detail"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "updateTime",column = "update_time"),
            @Result(property = "category",column = "category_id",
                    one = @One(select = "com.soton.shopping_centre.mapper.CategoryMapper.selectById")),
            @Result(property = "productSpecifications",column = "id",
                    many = @Many(select = "com.soton.shopping_centre.mapper.ProductSpecificationMapper.selectPdctSpecsByProductId"))
                 })
    @Select("select * from product")
    List<Product> selectAllProducts();

    @ResultMap("productMap")
    @Select("select * from product where ${ew.sqlSegment}")
    Product selectOneProductBy(@Param("ew") QueryWrapper<Product> wrapper);

    @ResultMap("productMap")
    @Select("select * from product where id=#{id}")
    Product selectOneProductById(Integer id);

    //@ResultMap("productMap")
    @Select("select * from product where category_id=#{cid}")
    List<Product> selectProductsByCategoryId(Integer cid);

    @ResultMap("productMap")
    @Select("select * from product where category_id=#{cid} and name like concat('%',#{pName},'%')")
    List<Product> selectProductsByCategoryIdAndProductName(Integer cid,String pName);

    @ResultMap("productMap")
    @Select("select * from product where name like concat('%',#{pName},'%') ")
    List<Product> selectProductsByProductName(String pName);
}
