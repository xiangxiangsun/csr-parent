package css.security.controller;

import css.security.entity.PageResult;
import css.security.entity.QueryPageBean;
import css.security.entity.Result;
import css.security.entity.SysRole;
import css.security.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    // 分页查询
    @RequestMapping("/findPage")

    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = roleService.findPage(
                    queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    // 添加
    @RequestMapping("/add")
    public Result add(@RequestBody SysRole role){
        try {
            roleService.add(role);
            return Result.success("新增角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("新增角色失败");
        }
    }

    // 删除
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            roleService.delete(id);
            return Result.success("删除角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除角色失败");
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<SysRole> list = roleService.findAll();
            return new Result(true, "查询权限成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询权限失败");
        }
    }

/*    //编辑
    @RequestMapping("edit")
    public Result edit(@RequestBody EditRole editRole){
        try {
            editRole.getSysRole().setId(roleService.getRoleId(editRole.getSysRole().getName()));
            roleService.edit(editRole.getSysRole(),editRole.getMenuIds(),editRole.getPermissionIds());
            return Result.success("编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("编辑失败");
        }
    }*/

    //编辑
    @RequestMapping("edit")
    public Result edit(@RequestBody SysRole role){
        try {
            roleService.edit(role);
            return Result.success("编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("编辑失败");
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            SysRole role = roleService.findById(id);
            return new Result(true, "角色信息查询成功",role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "角色信息查询失败");
        }
    }

    //根据角色查询所拥有菜单
    @RequestMapping("/findMenuIdsByRoleId")
    public List<Integer> findMenuIdsByRoleId(Integer id){
        return roleService.findMenuIdsByRoleId(id);
    }

    //根据角色ID查询所拥有的权限
    @RequestMapping("/findPermissionIdsByRoleId")
    public List<Integer> findPermissionIdsByRoleId(Integer id){ return  roleService.findPermissionIdsByRoleId(id); }
}
