package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.Order;
import com.soton.shopping_centre.pojo.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    public List<OrderDetail> queryAllOrderDetails();
    public OrderDetail queryOrderDetailById(Integer id);
    public List<OrderDetail> queryOrderDetailsByUserId(Integer userId);

    public int addOrderDetail(OrderDetail orderDetail);
    //public int deleteOrderDetailById(Integer id);
    public int deleteOrderDetailsByOrderId(Integer orderId);
    public int editOrderDetail(OrderDetail orderDetail);
}
