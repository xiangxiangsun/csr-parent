package css.security.mapper;

import css.security.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashSet;

@Mapper
public interface RoleMapper {
    @Select("select * from public.t_role where id = #{id}")
    SysRole selectById(Integer id);

    Integer selectIdByName(@Param("username") String username);

    HashSet<String> selectAllMenuByRoleId(Integer roleId);
}
