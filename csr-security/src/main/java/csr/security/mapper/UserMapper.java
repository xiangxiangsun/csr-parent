package csr.security.mapper;

import csr.security.entity.SysUser;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from public.user where id = #{id}")
    SysUser selectById(Integer id);

    @Select("select * from public.user where username = #{name}")
    SysUser selectByName(String name);
}
