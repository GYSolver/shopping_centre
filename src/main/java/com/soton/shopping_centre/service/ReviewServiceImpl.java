package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.ReviewMapper;
import com.soton.shopping_centre.pojo.Review;
import com.soton.shopping_centre.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewMapper reviewMapper;
    @Override
    public List<Review> queryAllReviews() {
        return null;
    }

    @Override
    public Review queryReviewById(Integer id) {
        return null;
    }

    @Override
    public List<Review> queryReviewByUserId(Integer userId) {
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Review> reviews = reviewMapper.selectList(wrapper);
        return reviews;
    }

    @Override
    public List<Review> queryReviewsByProductId(Integer productId) {
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",productId);
        List<Review> reviews = reviewMapper.selectList(wrapper);
        return reviews;
    }

    @Override
    public int addReview(Review review) { return reviewMapper.insert(review); }

    @Override
    public int deleteReviewById(Integer id) {
        return reviewMapper.deleteById(id);
    }

    @Override
    public int editReview(Review review) {
        return 0;
    }
}
