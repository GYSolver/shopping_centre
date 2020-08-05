package com.soton.shopping_centre.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.Specification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    @Results(id="categoryMap",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "name",column = "name"),
            @Result(property = "imagePath",column = "image_path"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "updateTime",column = "update_time"),
            @Result(property = "specifications",column = "id",
                    many = @Many(select = "com.soton.shopping_centre.mapper.SpecificationMapper.selectSpecificationsByCategoryId"))
    })
    @Select("select * from category where " + "${ew.sqlSegment}")
    Category selectOneCategoryBy(@Param("ew") QueryWrapper<Category> wrapper);

    @ResultMap("categoryMap")
    @Select("select * from category")
    List<Category> selectAllCategories();
}
