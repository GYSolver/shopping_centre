package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.OrderDetailMapper;
import com.soton.shopping_centre.pojo.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Override
    public List<OrderDetail> queryAllOrderDetails() {
        return null;
    }

    @Override
    public OrderDetail queryOrderDetailById(Integer id) {
        return orderDetailMapper.selectById(id);
    }

    @Override
    public List<OrderDetail> queryOrderDetailsByUserId(Integer userId) {
        return null;
    }

    @Override
    public int addOrderDetail(OrderDetail orderDetail) {
        return orderDetailMapper.insert(orderDetail);
    }

    @Override
    public int deleteOrderDetailsByOrderId(Integer orderId) {
        QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",orderId);
        return orderDetailMapper.delete(wrapper);
    }

    /*@Override
    public int deleteOrderDetailById(Integer id) {
        return 0;
    }*/

    @Override
    public int editOrderDetail(OrderDetail orderDetail) {
        return 0;
    }
}
