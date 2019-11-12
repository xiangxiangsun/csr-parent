package csr.security.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import csr.security.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface UserRoleMapper extends BaseMapper<SysUserRole> {
//    @Select("select * from public.user_role where user_id = #{userId}")
//    List<SysUserRole> listByUserId(Integer userId);

    List<SysUserRole> listByUserId(Integer userId);

}
