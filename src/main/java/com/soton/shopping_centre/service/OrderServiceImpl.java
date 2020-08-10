package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.OrderMapper;
import com.soton.shopping_centre.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderDetailService orderDetailService;

    @Override
    public List<Order> queryAllOrders() {
        return orderMapper.selectList(null);
    }

    @Override
    public Order queryOrderById(Integer id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<Order> queryOrdersByUserId(Integer userId) {
        /*QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);*/
        return orderMapper.selectOrdersByUserId(userId);
    }

    /*@Override
    public Order queryOrderByProductIdAndPSId(Integer userId, Integer pId, Integer psId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("product_id",pId);
        wrapper.eq("product_specification_id",psId);
        return orderMapper.selectOne(wrapper);
    }*/

    @Override
    public int addOrder(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public int deleteOrderById(Integer id) {
        orderDetailService.deleteOrderDetailsByOrderId(id);
        return orderMapper.deleteById(id);
    }

    @Override
    public int editOrder(Order order) {
        return orderMapper.updateById(order);
    }
}
