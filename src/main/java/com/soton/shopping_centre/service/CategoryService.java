package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> queryAllCategories();
    public Category queryCategoryById(Integer id);
    public Category queryCategoryByName(String name);

    public int addCategory(Category category);
    public int deleteCategoryById(Integer id);
    public int editCategory(Category category);
}
