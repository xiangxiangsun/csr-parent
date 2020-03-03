package css.security.service.impl;

import css.security.common.MessageConstant;
import css.security.common.enums.ExceptionEnum;
import css.security.common.exception.CssException;
import css.security.dao.DeptDao;
import css.security.entity.Dept;
import css.security.entity.TreeSelect;
import css.security.service.DeptService;
import css.security.utils.SecurityUtils;
import css.security.utils.StringUtils;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptDao deptDao;

    @Override
    public List<Dept> findTree() {
//        Map<String, Object> data = new HashMap<String, Object>();
        try {//查询所有菜单
            List<Dept> allDept = deptDao.findTree();
            //根节点
            List<Dept> rootDept = new ArrayList<Dept>();
            for (Dept nav : allDept) {
                if (nav.getParentId() == null || nav.getParentId().toString().equals("")) {//首节点是NULL的，为根节点
                    rootDept.add(nav);
                }
                if (nav.getParentId() == 0){//首节点是0的，为根节点
                    rootDept.add(nav);
                }
            }
            /* 根据Dept类的order排序 */
            Collections.sort(rootDept, order());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Dept nav : rootDept) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Dept> childList = getChild(String.valueOf(nav.getDeptId()), allDept);
                nav.setChildren(childList);//给根节点设置子节点
            }
            /**
             * 输出构建好的菜单数据。
             *
             */
            return rootDept;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        return null;
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<Dept> depts) {
        return depts.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public Dept selectDeptById(Long deptId) {
        return deptDao.selectDeptById(deptId);
    }

    @Override
    public String checkDeptNameUnique(Dept dept){
        Long deptId = Objects.nonNull(dept.getDeptId()) ? dept.getDeptId() : -1L;

        Dept info = deptDao.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return MessageConstant.NOT_UNIQUE;
        }
        return MessageConstant.UNIQUE;
    }

    @Override
    public int updateDept(Dept dept) {
        Dept newParentDept = deptDao.selectDeptById(dept.getParentId());
        Dept oldDept = deptDao.selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptDao.updateDept(dept);
        if (MessageConstant.UNIQUE.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    @Override
    public int insertDept(Dept dept) {
        Dept deptById = deptDao.selectDeptById(dept.getParentId());
        if (MessageConstant.NOT_UNIQUE.equals(deptById.getStatus())){
            throw new CssException(ExceptionEnum.INSERT_DEPT_ERROR);
        }
        dept.setAncestors(deptById.getAncestors()+","+dept.getParentId());
        dept.setCreateBy(SecurityUtils.getUsername());
        dept.setDelFlag("0");
        return deptDao.insertDept(dept);
    }

    @Override
    public int deleteDept(Long deptId) {
        return deptDao.deleteDeptById(deptId);
    }

    @Override
    public boolean hasChildByDeptId(Long deptId) {
        int result = deptDao.hasChildByDeptId(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(Dept dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = deptDao.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptDao.updateDeptStatus(dept);
    }

    // 查询子菜单
    public List<Dept> getChild(String id, List<Dept> allDept) {
        //子菜单
        List<Dept> childList = new ArrayList<Dept>();
        for (Dept nav : allDept) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getParentId() != null && String.valueOf(nav.getParentId()).equals(id)) {
                childList.add(nav);
            }
        }
        //递归
        for (Dept nav : childList) {
            nav.setChildren(getChild(String.valueOf(nav.getDeptId()), allDept));
        }
        Collections.sort(childList, order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<Dept>();
        }
        return childList;
    }

    /**
     * 修改子元素关系
     *
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<Dept> children = deptDao.selectChildrenDeptById(deptId);
        for (Dept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptDao.updateDeptChildren(children);
        }
    }

    public Comparator<Dept> order() {
        Comparator<Dept> comparator = new Comparator<Dept>() {
            @Override
            public int compare(Dept o1, Dept o2) {
                Integer id1 = Integer.parseInt(o1.getOrderNum());
                Integer id2 = Integer.parseInt(o2.getOrderNum());
                return (id1 > id2) ? 1 : ( (id1.equals(id2)) ? 0 : -1);
            }
        };
        return comparator;
    }


    @Override
    public List<Dept> selectDeptList(Dept dept) {
        return deptDao.selectDeptList(dept);
    }

    @Override
    public List<Dept> buildDeptTree(List<Dept> depts) {
        List<Dept> returnList = new ArrayList<Dept>();
        //遍历查询到的list
        for (Iterator<Dept> iterator = depts.iterator(); iterator.hasNext();)
        {
            Dept t = (Dept) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == 0)
            {
                recursionFn(depts, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptDao.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<Dept> list, Dept t)
    {
        // 得到子节点列表
        List<Dept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Dept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<Dept> it = childList.iterator();
                while (it.hasNext())
                {
                    Dept n = (Dept) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Dept> getChildList(List<Dept> list, Dept t)
    {
        List<Dept> tlist = new ArrayList<Dept>();
        Iterator<Dept> it = list.iterator();
        while (it.hasNext())
        {
            Dept n = (Dept) it.next();
            if (n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Dept> list, Dept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
