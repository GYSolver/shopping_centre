package com.soton.shopping_centre.controller.frontstage;

import com.soton.shopping_centre.pojo.*;
import com.soton.shopping_centre.service.OrderDetailService;
import com.soton.shopping_centre.service.ReviewService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Review> reviews = reviewService.queryAllReviews();
        model.addAttribute("reviews",reviews);
        return "/dashboard/review/index";
    }

    @GetMapping("/add/{orderDetailId}")
    public String onGetAdd(@PathVariable Integer orderDetailId,Model model){
        OrderDetail orderDetail = orderDetailService.queryOrderDetailById(orderDetailId);
        model.addAttribute("orderDetail",orderDetail);
        return "/front-stage/add-review";
    }

    @PostMapping("/add")
    public String onPostAdd(Review review){
        if(review!=null){
            //get current user
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            if(user!=null){
                review.setUserId(user.getId());
                review.setUsername(user.getUsername());
                reviewService.addReview(review);
                return "redirect:/my-orders";
            }
            else
                return "/front-stage/unauthorized";
        }
        else
            return "/front-stage/404";
    }


}
