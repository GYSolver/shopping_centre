package com.soton.shopping_centre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Cart;
import com.soton.shopping_centre.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface CartMapper extends BaseMapper<Cart> {
    @Results(id="cartMap",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "productId",column = "product_id"),
            @Result(property = "productSpecificationId",column = "product_specification_id"),
            @Result(property = "quantity",column = "quantity"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "updateTime",column = "update_time"),
            @Result(property = "productSpecification",column = "product_specification_id",
                    one = @One(select = "com.soton.shopping_centre.mapper.ProductSpecificationMapper.selectPdctSpecById"))
    })
    @Select("select * from cart")
    List<Cart> selectAllCarts();

    @ResultMap("cartMap")
    @Select("select * from cart where user_id=#{userId}")
    List<Cart> selectCartsByUserId(@PathVariable Integer userId);
}
