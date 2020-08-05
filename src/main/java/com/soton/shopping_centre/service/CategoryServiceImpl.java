package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.CategoryMapper;
import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ProductService productService;
    @Autowired
    SpecificationService specificationService;

    @Override
    public List<Category> queryAllCategories() {
        //return categoryMapper.selectList(null);
        return  categoryMapper.selectAllCategories();
    }

    @Override
    public Category queryCategoryById(Integer id) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category.id",id);
        return categoryMapper.selectOneCategoryBy(queryWrapper);
    }

    @Override
    public Category queryCategoryByName(String name) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return categoryMapper.selectOneCategoryBy(queryWrapper);
    }

    @Override
    public int addCategory(Category category) { return categoryMapper.insert(category); }

    @Override
    public int deleteCategoryById(Integer id) {
        //delete products
        List<Product> products = productService.queryProductsByCategoryId(id);
        for (Product product : products) {
            productService.deleteProductById(product.getId());
        }

        //delete specifications
        List<Specification> specifications = specificationService.querySpecificationsByCategoryId(id);
        for (Specification specification : specifications) {
            specificationService.deleteSpecificationById(specification.getId());
        }
        //delete category
        return categoryMapper.deleteById(id);
    }

    @Override
    public int editCategory(Category category) { return categoryMapper.updateById(category); }
}
