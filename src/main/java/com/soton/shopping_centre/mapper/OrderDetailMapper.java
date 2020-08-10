package com.soton.shopping_centre.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Order;
import com.soton.shopping_centre.pojo.OrderDetail;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    @Select("select * from order_detail where order_id=#{orderId}")
    List<OrderDetail> selectOrderDetailsByOrderId(@PathVariable Integer orderId);
}
