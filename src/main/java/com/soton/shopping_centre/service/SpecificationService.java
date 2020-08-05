package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.Specification;

import java.util.List;

public interface SpecificationService {
    public List<Specification> queryAllSpecifications();
    public Specification querySpecificationById(Integer id);
    public List<Specification> querySpecificationsByKey(String key);
    public List<Specification> querySpecificationsByCategoryId(Integer categoryId);

    public int addSpecification(Specification specification);
    public int deleteSpecificationById(Integer id);
    public int editSpecification(Specification specification);
}
