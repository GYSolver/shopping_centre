package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.Cart;

import java.util.List;

public interface CartService {
    public List<Cart> queryAllCarts();
    public Cart queryCartById(Integer id);
    public List<Cart> queryCartsByUserId(Integer userId);

    public int addCart(Cart cart);
    public int deleteCartById(Integer id);
    public int editCart(Cart cart);
}
