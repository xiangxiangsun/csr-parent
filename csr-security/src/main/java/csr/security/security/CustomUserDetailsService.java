package csr.security.security;

import csr.security.entity.SysRole;
import csr.security.entity.SysUser;
import csr.security.entity.SysUserRole;
import csr.security.service.RoleService;
import csr.security.service.UserRoleService;
import csr.security.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 从数据库中取出用户信息
        SysUser user = userService.selectByName(username);

        // 判断用户是否存在
        if(user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 添加权限
        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
//
//        System.out.println("------------");
//        System.out.println(userService.selectByName(username));
//        System.out.println(user.getId());
//        System.out.println(userRoles);
//        System.out.println(userRoleService.listByUserId(1));
//        System.out.println("------------");

        for (SysUserRole sysUserRole : userRoles) {
            SysRole role = roleService.selectById(sysUserRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        // 返回UserDetails实现类
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
