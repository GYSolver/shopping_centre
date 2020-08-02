package com.soton.shopping_centre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
}
