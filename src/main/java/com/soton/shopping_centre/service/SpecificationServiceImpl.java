package com.soton.shopping_centre.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soton.shopping_centre.mapper.ProductMapper;
import com.soton.shopping_centre.mapper.SpecificationMapper;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService{
    @Autowired
    SpecificationMapper specificationMapper;
    @Autowired
    ProductSpecificationService productSpecificationService;

    @Override
    public List<Specification> queryAllSpecifications() {
        return specificationMapper.selectList(null);
    }

    @Override
    public Specification querySpecificationById(Integer id) {
        QueryWrapper<Specification> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return specificationMapper.selectOne(wrapper);
    }

    @Override
    public List<Specification> querySpecificationsByKey(String key) {
        QueryWrapper<Specification> wrapper = new QueryWrapper<>();
        wrapper.eq("key",key);
        return specificationMapper.selectList(wrapper);
    }

    @Override
    public List<Specification> querySpecificationsByCategoryId(Integer categoryId) {
        QueryWrapper<Specification> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id",categoryId);
        return specificationMapper.selectList(wrapper);
    }

    @Override
    public int addSpecification(Specification specification) {
        return specificationMapper.insert(specification);
    }

    @Override
    public int deleteSpecificationById(Integer id) {
        return specificationMapper.deleteById(id);
    }

    @Override
    public int editSpecification(Specification specification) {
        return specificationMapper.updateById(specification);
    }
}
