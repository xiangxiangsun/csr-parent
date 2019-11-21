package css.security.service;

import css.security.entity.PageResult;
import css.security.entity.SysUser;

import java.util.List;

public interface UserService {
    SysUser loadUserByUsername(String username);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void add(SysUser user, Integer[] roleIds);

    SysUser findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(SysUser user, Integer[] roleIds);

    void deleteById(Integer id);
}
