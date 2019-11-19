package csr.security.service;

import csr.security.entity.PageResult;
import csr.security.entity.Permission;

import java.util.List;

public interface PermissionService {
    void add(Permission permission);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Permission findById(Integer id);

    void edit(Permission permission);

    void delete(Integer id);

    List<Permission> findAll();
}
