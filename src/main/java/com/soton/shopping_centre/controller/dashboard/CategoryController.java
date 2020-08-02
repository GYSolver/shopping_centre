package com.soton.shopping_centre.controller.dashboard;

import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Category> categories = categoryService.queryAllCategories();
        model.addAttribute("categories",categories);
        return "/dashboard/category/index";
    }
    @GetMapping("/add")
    public String onGetAdd(){ return "/dashboard/category/add"; }

    @PostMapping("/add")
    public String onPostAdd(Category category){
        if(category.getName()!=null){
            categoryService.addCategory(category);
            return "redirect:/dashboard/category/";
        }
        else
            return "/404-page";
    }

    @GetMapping("/edit/{id}")
    public String onGetEdit(@PathVariable Integer id, Model model){
        //category
        Category category = categoryService.queryCategoryById(id);
        model.addAttribute("category",category);
        return "/dashboard/category/edit";
    }

    @PostMapping("/edit")
    public String onPostEdit(Category category){
        if(category.getName()!=null){
            categoryService.editCategory(category);
            return "redirect:/dashboard/category/";
        }
        else
            return "/404-page";
    }


    @PostMapping("/delete/{id}")
    public String onPostDelete(@PathVariable Integer id){
        categoryService.deleteCategoryById(id);
        return "redirect:/dashboard/category/";
    }
}
