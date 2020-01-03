package css.security.dao;

import com.github.pagehelper.Page;
import css.security.dto.SysUserDTO;
import css.security.entity.Dept;
import css.security.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    SysUser findByUserName(@Param("username") String username);

    List<SysUser> findPage(@Param("queryString") String queryString);

//    Integer add(@Param("user")SysUser user,@Param("password")String password);
    Integer add(@Param("user")SysUser user);

    void setUserAndRole(Map<String, Integer> map);

    SysUser findById(@Param("id") Integer id);

    List<Integer> findRoleIdsByUserId(@Param("id")Integer id);

    void edit(SysUser user);

    void deleteRoleIdByUserId(@Param("id") Integer id);

    void deleteByUserId(Integer id);

    List<SysUser> findUserPage();

    Dept selectByDeptId(Integer deptid);

    List<SysUser> selectUserList(@Param("user") SysUserDTO userDTO);

    List<Integer> findDeptIdsByUserId(@Param("id")Integer id);

    void setUserAndDept(Map<String, Integer> map);

    void deleteDeptIdByUserId(@Param("id")Integer id);

    List<Integer> selectUserListByDept(@Param("id")Integer id);
}
