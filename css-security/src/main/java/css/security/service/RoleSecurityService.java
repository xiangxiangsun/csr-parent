package css.security.service;

import css.security.entity.SysRole;
import css.security.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleSecurityService {

    @Resource
    private RoleMapper roleMapper;

    public SysRole selectById(Integer id){
        return roleMapper.selectById(id);
    }
}
