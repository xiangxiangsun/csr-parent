package css.security.service;

import css.security.dto.SysUserDTO;
import css.security.entity.PageResult;
import css.security.entity.SysUser;
import css.security.entity.UserTable;

import java.util.List;

public interface UserService {
    SysUser loadUserByUsername(String username);

    PageResult<SysUserDTO> findPage(SysUserDTO userDTO);

    void add(UserTable user);

    SysUser findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(UserTable user);

    void deleteById(Integer id);

    List<Integer> findDeptIdsByUserId(Integer id);
}
