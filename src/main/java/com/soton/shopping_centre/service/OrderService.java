package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.Cart;
import com.soton.shopping_centre.pojo.Order;

import java.util.List;

public interface OrderService {
    public List<Order> queryAllOrders();
    public Order queryOrderById(Integer id);
    public List<Order> queryOrdersByUserId(Integer userId);
    //public Order queryOrderByProductIdAndPSId(Integer userId, Integer pId,Integer psId);

    public int addOrder(Order order);
    public int deleteOrderById(Integer id);
    public int editOrder(Order order);
}
