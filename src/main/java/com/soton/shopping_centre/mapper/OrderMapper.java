package com.soton.shopping_centre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {
    @Results(id="orderMap",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "itemsCount",column = "items_count"),
            @Result(property = "totalPrice",column = "total_price"),
            @Result(property = "status",column = "status"),
            @Result(property = "paymentInfo",column = "payment_info"),
            @Result(property = "address",column = "address"),
            @Result(property = "postcode",column = "postcode"),
            @Result(property = "firstName",column = "first_name"),
            @Result(property = "lastName",column = "last_name"),
            @Result(property = "email",column = "email"),
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "username",column = "username"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "updateTime",column = "update_time"),
            @Result(property = "orderDetails",column = "id",
                    many = @Many(select = "com.soton.shopping_centre.mapper.OrderDetailMapper.selectOrderDetailsByOrderId"))
    })
    @Select("select * from `order` where user_id=#{userId}")
    List<Order> selectOrdersByUserId(@PathVariable Integer userId);

    @ResultMap("orderMap")
    @Select("select * from `order`")
    List<Order> selectAllOrders();

    /*@ResultMap("orderMap")
    @Select("select * from cart order user_id=#{userId} and product_id=#{pId} and product_specification_id=#{psId}" )
    Order selectOrderByProductIdAndPSId(@PathVariable Integer userId,@PathVariable Integer pId,@PathVariable Integer psId);*/
}
