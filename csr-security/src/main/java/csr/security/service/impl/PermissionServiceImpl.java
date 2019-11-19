package csr.security.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import csr.security.dao.PermissionDao;
import csr.security.entity.PageResult;
import csr.security.entity.Permission;
import csr.security.service.PermissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionDao permissionDao;

    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Permission> page = permissionDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Permission findById(Integer id) {
        return permissionDao.findById(id);
    }

    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

    @Override
    public void delete(Integer id) {
        permissionDao.delete(id);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
