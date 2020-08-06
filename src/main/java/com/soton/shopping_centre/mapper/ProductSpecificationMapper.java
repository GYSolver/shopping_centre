package com.soton.shopping_centre.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.ProductSpecification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductSpecificationMapper extends BaseMapper<ProductSpecification> {
    @Results(id="pdctSpecMap",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "productId",column = "product_id"),
            @Result(property = "specification",column = "specification"),
            @Result(property = "price",column = "price"),
            @Result(property = "stock",column = "stock"),
            @Result(property = "imagePath",column = "image_path"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "updateTime",column = "update_time"),
            @Result(property = "product",column = "product_id",
                    one = @One(select = "com.soton.shopping_centre.mapper.ProductMapper.selectById")),
    })
    @Select("select * from product_specification where " + "${ew.sqlSegment}")
    List<ProductSpecification> selectPdctSpecBy(@Param("ew") QueryWrapper<ProductSpecification> wrapper);

    @ResultMap("pdctSpecMap")
    @Select("select * from product_specification where product_specification.product_id=#{productId}")
    List<ProductSpecification> selectPdctSpecsByProductId(Integer productId);

    @ResultMap("pdctSpecMap")
    @Select("select * from product_specification where product_specification.id=#{id}")
    ProductSpecification selectPdctSpecById(Integer id);
}
