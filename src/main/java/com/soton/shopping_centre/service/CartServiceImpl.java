package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.CartMapper;
import com.soton.shopping_centre.pojo.Cart;
import com.soton.shopping_centre.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;

    @Override
    public List<Cart> queryAllCarts() {
        return cartMapper.selectAllCarts();
        //return cartMapper.selectList(null);
    }

    @Override
    public Cart queryCartById(Integer id) {
        return cartMapper.selectById(id);
    }

    @Override
    public List<Cart> queryCartsByUserId(Integer userId) {
        /*QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("userId",userId);
        return cartMapper.selectList(wrapper);*/
        return cartMapper.selectCartsByUserId(userId);
    }

    @Override
    public Cart queryCartByProductIdAndPSId(Integer userId,Integer pId, Integer psId) {
        return cartMapper.selectCartByProductIdAndPSId(userId,pId,psId);
    }

    @Override
    public int addCart(Cart cart) {
        return cartMapper.insert(cart);
    }

    @Override
    public int deleteCartById(Integer id) {
        return cartMapper.deleteById(id);
    }

    @Override
    public int editCart(Cart cart) {
        return cartMapper.updateById(cart);
    }
}
