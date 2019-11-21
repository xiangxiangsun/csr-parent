package css.security.mapper;

import css.security.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from public.t_user where id = #{id}")
    SysUser selectById(Integer id);

    @Select("select * from public.t_user where username = #{name}")
    SysUser selectByName(String name);
}
