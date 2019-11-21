package css.security.service;

import css.security.entity.SysUser;
import css.security.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserSecurityService {

    @Resource
    private UserMapper userMapper;

    public SysUser selectById(Integer id){
        return userMapper.selectById(id);
    }

    public SysUser selectByName(String name){
        return userMapper.selectByName(name);
    }
}
