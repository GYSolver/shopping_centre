package com.soton.shopping_centre.controller.dashboard;

import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Specification;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.SpecificationService;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@RequestMapping("/dashboard/specification")
@Controller
public class SpecificationController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    SpecificationService specificationService;


    @GetMapping("/add/{categoryId}")
    public String onGetAdd(@PathVariable Integer categoryId, Model model){
        //model.addAttribute("categoryId",categoryId);
        return "/dashboard/category/specification/add";
    }

    @PostMapping("/add")
    public String onPostAdd(int categoryId,String[] sName,String[] sValue){
        Category category = categoryService.queryCategoryById(categoryId);
        if(category!=null){
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
        Specification specification = specificationService.querySpecificationById(id);
        model.addAttribute("specification",specification);
        return "/dashboard/category/specification/edit";
    }

    @PostMapping("/edit")
    public String onPostEdit(Specification specification){
        if(specification.getName()!=null){
            specificationService.editSpecification(specification);
            return "redirect:/dashboard/category/";
        }
        else
            return "/dashboard/404";
    }


    @PostMapping("/delete/{id}")
    public String onPostDelete(@PathVariable Integer id){
        specificationService.deleteSpecificationById(id);
        //productSpecifications are also deleted in SpecificationService
        return "redirect:/dashboard/category/";
    }

    //query specified specifications and format them according to provided categoryId for frontend
    @PostMapping("/{categoryId}")
    @ResponseBody
    public List<List<Specification>> onGetSpec(@PathVariable Integer categoryId){
        List<List<Specification>> lists= new ArrayList<List<Specification>>();
        List<Specification> specifications = specificationService.querySpecificationsByCategoryId(categoryId);


        for(int i=0;i<specifications.size();i++){
            Specification specification=specifications.get(i);
            List<Specification> list = new ArrayList<>();
            list.add(specification);
            for(int j=i+1;j<specifications.size();j++){
                Specification specificationJ=specifications.get(j);
                if(specification.getName().equals(specificationJ.getName())){
                    list.add(specificationJ);
                    specifications.remove(specificationJ);
                    j--;
                }
            }
            lists.add(list);
        }
        return lists;
    }
}
