package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.CategoryMapper;
import com.soton.shopping_centre.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> queryAllCategories() { return categoryMapper.selectList(null); }

    @Override
    public Category queryCategoryById(Integer id) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return categoryMapper.selectOne(queryWrapper);
    }

    @Override
    public Category queryCategoryByName(String name) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return categoryMapper.selectOne(queryWrapper);
    }

    @Override
    public int addCategory(Category category) { return categoryMapper.insert(category); }

    @Override
    public int deleteCategoryById(Integer id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public int editCategory(Category category) { return categoryMapper.updateById(category); }
}
