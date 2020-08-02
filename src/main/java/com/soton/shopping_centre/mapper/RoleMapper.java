package com.soton.shopping_centre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soton.shopping_centre.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {
/*    List<Role> queryAllRoles();
    Role queryRoleById(Integer id);
    int addRole(Role role);
    int deleteRoleById(Integer id);
    int editRole(Role role);*/
}
