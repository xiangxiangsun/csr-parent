package csr.security.mapper;

import csr.security.entity.SysRole;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper {
    @Select("select * from public.role where id = #{id}")
    SysRole selectById(Integer id);
}
