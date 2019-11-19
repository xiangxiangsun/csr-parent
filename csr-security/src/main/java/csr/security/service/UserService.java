package csr.security.service;

import csr.security.entity.PageResult;
import csr.security.entity.SysUser;
import org.springframework.stereotype.Component;

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
