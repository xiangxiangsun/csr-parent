package css.security.controller;

import css.security.entity.PageResult;
import css.security.entity.Permission;
import css.security.entity.QueryPageBean;
import css.security.entity.Result;
import css.security.service.PermissionService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加失败");
        }
        return Result.success("添加成功");

    }

    // 分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());

        return pageResult;

    }

    // 编辑权限
    @RequestMapping("/findById")
    public Result findById(Integer id){

        try {
            Permission permission = permissionService.findById(id);
            return Result.success("",permission);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询失败");
        }

    }
    // 更新
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("编辑失败");

        }
        return Result.success("编辑成功");
    }

    // 删除
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            permissionService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
        return Result.success("删除成功");
    }

    // 查询所有
    @RequestMapping("/findAll")
    public Result findAll(){

        List<Permission> permissions = permissionService.findAll();
        if (permissions != null && permissions.size() > 0){

            return Result.success("查询权限成功",permissions);
        }else {
            return Result.error("获取权限失败请刷新页面");
        }
    }
}
