package css.security.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import css.security.dao.RoleDao;
import css.security.entity.PageResult;
import css.security.entity.SysRole;
import css.security.service.RoleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<SysRole> page = roleDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(SysRole role) {
        // 将数据插入到t_role表中
        roleDao.add(role);

        // 插入数据到中间表t_role_menu中
        Integer roleId = role.getId();
        List<Integer> menuIds = role.getMenuIds();
        List<Map> maps = addRelationBatchs(roleId, menuIds);
        if (maps != null && maps.size() > 0){
            roleDao.addRoleAndMenu(maps);
        }

        // 插入数据到中间表t_role_permission中
        List<Integer> permissionIds = role.getPermissionIds();
        List<Map> mapList = addRelationBatchs(roleId, permissionIds);
        if (mapList != null && mapList.size() > 0){
            roleDao.addRoleAndPermission(mapList);
        }
    }

    @Override
    public void edit(SysRole role) {
        //先将角色基本信息进行修改
        roleDao.edit(role);
        //通过roleId对已有中间表进行删除
        roleDao.deleteRoleAndMenu(role.getId());
        roleDao.deleteRoleAndPermission(role.getId());
        //再次添加
        setMenuIdByRoleId(role.getId(),role.getMenuIds());
        setPermissionIdByRoleId(role.getId(),role.getPermissionIds());
    }

    // 删除
    @Override
    public void delete(Integer id) {
        // 先删关系表数据
        deleteRelation(id);

        // 再删除t_role表中对应数据
        roleDao.delete(id);
    }

    @Override
    public List<SysRole> findAll() {
        return roleDao.findAll();
    }

    @Override
    public SysRole findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Integer> findMenuIdsByRoleId(Integer id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }

//    @Override
//    public Integer getRoleId(String name) {
//        return roleDao.getRoleId(name);
//    }

    private void deleteRelation(Integer id) {
        // 删除关系表t_role_menu表中对应数据
        roleDao.deleteRoleAndMenu(id);
        // 删除关系表t_role_permission表中对应数据
        roleDao.deleteRoleAndPermission(id);
    }


    private List<Map> addRelationBatchs(Integer mainId, List<Integer> ids){
        List<Map> batchs = new ArrayList<>();
        if (ids != null && ids.size() > 0){
            for (Integer id : ids) {
                Map<String,Integer> map = new HashMap<>();
                map.put("mainId",mainId);
                map.put("id",id);
                batchs.add(map);
            }
            return batchs;
        } else {
            return null;
        }
    }

    public void setMenuIdByRoleId(Integer roleId,List<Integer> menuIds){
        if (menuIds != null && menuIds.size()>0){
            for (Integer menuId: menuIds) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("role_id",roleId);
                map.put("menu_id",menuId);
                roleDao.setMenuIdByRoleId(map);
            }
        }
    }

    public void setPermissionIdByRoleId(Integer roleId,List<Integer> permissionIds){
        if (permissionIds != null && permissionIds.size()>0){
            for (Integer permissionId: permissionIds) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("role_id",roleId);
                map.put("permission_id",permissionId);
                roleDao.setPermissionIdByRoleId(map);
            }
        }
    }
}
