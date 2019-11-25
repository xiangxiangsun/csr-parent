package css.security.service;

import css.security.entity.PageResult;
import css.security.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void add(SysRole role);

    void edit(SysRole role);

    void delete(Integer id);

    List<SysRole> findAll();

    SysRole findById(@Param("id") Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

//    Integer getRoleId(String name);
}
