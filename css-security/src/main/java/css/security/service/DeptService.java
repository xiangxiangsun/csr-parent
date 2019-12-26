package css.security.service;

import css.security.entity.Dept;
import css.security.entity.TreeSelect;

import java.util.List;

public interface DeptService {

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
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

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildDeptTreeSelect(List<Dept> depts);

    public Dept selectDeptById(Long deptId);

    public String checkDeptNameUnique(Dept dept);

    public int updateDept(Dept dept);

    public int insertDept(Dept dept);

    public int deleteDept(Long deptId);

    boolean hasChildByDeptId(Long deptId);
}
