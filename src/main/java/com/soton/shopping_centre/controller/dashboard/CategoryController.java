package com.soton.shopping_centre.controller.dashboard;

import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Specification;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    SpecificationService specificationService;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Category> categories = categoryService.queryAllCategories();
        model.addAttribute("categories",categories);
        return "/dashboard/category/index";
    }
    @GetMapping("/add")
    public String onGetAdd(){ return "/dashboard/category/add"; }

    @PostMapping("/add")
    public String onPostAdd(Category category,String[] sName,String[] sValue){
        if(category.getName()!=null){
            categoryService.addCategory(category);
            for (int i=0;sName!=null&&i<sName.length;i++) {
                Specification specification = new Specification();
                specification.setCategoryId(category.getId());
                specification.setName(sName[i]);
                specification.setValue(sValue[i]);
                specificationService.addSpecification(specification);
            }
            return "redirect:/dashboard/category/";
        }
        else
            return "/dashboard/404";
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
            return "/dashboard/404";
    }


    @PostMapping("/delete/{id}")
    public String onPostDelete(@PathVariable Integer id){
        categoryService.deleteCategoryById(id);
        //products are also deleted in CategoryService
        //specifications are also deleted in CategoryService
        return "redirect:/dashboard/category/";
    }
}
