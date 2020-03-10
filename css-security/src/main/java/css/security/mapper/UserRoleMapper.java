package css.security.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import css.security.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<SysUserRole> {

    List<SysUserRole> listByUserId(Integer userId);

    String selectByUserId(Integer userId);
}
