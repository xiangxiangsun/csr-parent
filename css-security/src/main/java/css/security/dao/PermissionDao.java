package css.security.dao;

import com.github.pagehelper.Page;
import css.security.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(@Param("roleId") Integer roleId);

    void add(Permission permission);

    Page<Permission> findPage(@Param("queryString") String queryString);

    Permission findById(@Param("id")Integer id);

    void edit(Permission permission);

    void delete(@Param("id")Integer id);
    List<Permission> findAll();

}
