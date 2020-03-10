package css.security.service;

import css.security.entity.SysUserRole;
import css.security.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    public List<SysUserRole> listByUserId(Integer userId){
        return userRoleMapper.listByUserId(userId);
    }

    public String selectByUserId(Integer userId) {
        return userRoleMapper.selectByUserId(userId);
    }
}
