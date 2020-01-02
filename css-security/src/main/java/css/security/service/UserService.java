package css.security.service;

import css.security.dto.SysUserDTO;
import css.security.entity.PageResult;
import css.security.entity.SysUser;

import java.util.List;

public interface UserService {
    SysUser loadUserByUsername(String username);

    PageResult<SysUserDTO> findPage(SysUserDTO userDTO);

    void add(SysUser user, Integer[] roleIds);

    SysUser findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(SysUser user, Integer[] roleIds);

    void deleteById(Integer id);
}
