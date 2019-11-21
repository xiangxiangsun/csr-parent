package css.security.dao;

import com.github.pagehelper.Page;
import css.security.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    SysUser findByUserName(@Param("username") String username);

    Page<SysUser> findPage(@Param("queryString") String queryString);

    Integer add(SysUser user);

    void setUserAndRole(Map<String, Integer> map);

    SysUser findById(@Param("id") Integer id);

    List<Integer> findRoleIdsByUserId(@Param("id")Integer id);

    void edit(SysUser user);

    void deleteRoleIdByUserId(@Param("id") Integer id);

    void deleteByUserId(Integer id);
}
