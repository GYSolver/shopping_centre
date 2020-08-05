package com.soton.shopping_centre.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Specification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpecificationMapper extends BaseMapper<Specification> {

    @Select("select * from specification where category_id = #{categoryId}")
    List<Specification> selectSpecificationsByCategoryId(int categoryId);

   /* @Select("select * from specification where id=#{id}")
    Specification selectSpecificationById(int id);*/
}
