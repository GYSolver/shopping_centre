package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.Review;

import java.util.List;

public interface ReviewService {
    public List<Review> queryAllReviews();
    public Review queryReviewById(Integer id);
    public List<Review> queryReviewByUserId(Integer userId);
    public List<Review> queryReviewsByProductId(Integer productId);

    public int addReview(Review review);
    public int deleteReviewById(Integer id);
    public int editReview(Review review);
}
