package css.security.dao;

import css.security.entity.Dept;
import css.security.entity.TreeSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DeptDao {

    List<Dept> selectChildrenDeptById(Long deptId);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<Dept> findTree();

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    public List<Integer> selectDeptListByRoleId(Long roleId);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
//    public List<TreeSelect> buildDeptTreeSelect(List<Dept> depts);

    public Dept selectDeptById(Long deptId);

    public int updateDept(Dept dept);

    public void updateDeptStatus(Dept dept);

    public int updateDeptChildren(@Param("depts") List<Dept> depts);

    public int insertDept(Dept dept);

    public int deleteDeptById(Long deptId);

    public int hasChildByDeptId(Long deptId);
}
