package css.security.service.impl;

import css.security.dao.DeptDao;
import css.security.entity.Dept;
import css.security.entity.TreeSelect;
import css.security.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public List<Dept> findTree() {
        Map<String, Object> data = new HashMap<String, Object>();
        try {//查询所有菜单
            List<Dept> allDept = deptDao.findTree();
            //根节点
            List<Dept> rootDept = new ArrayList<Dept>();
            for (Dept nav : allDept) {
                if (nav.getParentId() == null || nav.getParentId().equals("")) {//首节点是NULL的，为根节点
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
        List<Dept> deptTrees = findTree();
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public Dept selectDeptById(Integer deptId) {
        return deptDao.selectDeptById(deptId);
    }

    // 查询子菜单
    public List<Dept> getChild(String id, List<Dept> allDept) {
        //子菜单
        List<Dept> childList = new ArrayList<Dept>();
        for (Dept nav : allDept) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
//            System.out.println(nav.getParentId());

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
 * 校验部门名称是否唯一
**/



    public Comparator<Dept> order() {
        Comparator<Dept> comparator = new Comparator<Dept>() {
            @Override
            public int compare(Dept o1, Dept o2) {
                Integer id1 = Integer.parseInt(o1.getOrderNum());
                Integer id2 = Integer.parseInt(o2.getOrderNum());
                return (id1 > id2) ? 1 : ((id1 == id2) ? 0 : -1);
            }
        };
        return comparator;
    }
}
