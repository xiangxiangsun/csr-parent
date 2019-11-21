package css.security.mapper;

import css.security.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {
    @Select("select * from public.t_role where id = #{id}")
    SysRole selectById(Integer id);
}
