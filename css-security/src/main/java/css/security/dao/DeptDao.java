package css.security.dao;

import css.security.entity.Dept;
import css.security.entity.TreeSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptDao {

    public List<Dept> selectChildrenDeptById(Long deptId);

    /**
     * 查询部门管理数据
     *
     * @return 部门信息集合
     */
    public List<Dept> findTree();

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    public List<Integer> selectDeptListByRoleId(Long roleId);

    public Dept selectDeptById(Long deptId);

    public int updateDept(Dept dept);

    public void updateDeptStatus(Dept dept);

    public int updateDeptChildren(@Param("depts") List<Dept> depts);

    public int insertDept(Dept dept);

    public int deleteDeptById(Long deptId);

    public int hasChildByDeptId(Long deptId);

    public Dept checkDeptNameUnique(@Param("deptName") String deptName,@Param("parentId") Long parentId);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<Dept> selectDeptList(Dept dept);

    int checkDeptExistUser(Long deptId);
}
