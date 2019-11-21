package css.security.controller;

import css.security.common.MessageConstant;
import css.security.entity.*;
import css.security.service.UserService;
import css.security.entity.PageResult;
import css.security.entity.QueryPageBean;
import css.security.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getUserName")
    public Result getUsername(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
    }

    //分页查询
//    @PreAuthorize("hasAuthority('USER_QUERY')")
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = userService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody SysUser user, Integer[] roleIds){
        try {
            userService.add(user,roleIds);
            return new Result(true, "用户新建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "用户新建失败");
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            SysUser user = userService.findById(id);
            return new Result(true, "用户信息查询成功",user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "用户信息查询失败");
        }

    }

    @RequestMapping("/findRoleIdsByUserId")
    public List<Integer> findRoleIdsByUserId(Integer id){
        return userService.findRoleIdsByUserId(id);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody SysUser user , Integer[] roleIds){
        try {
            userService.edit(user,roleIds);
            return new Result(true,"用户信息修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"用户信息修改失败");
        }
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            userService.deleteById(id);
            return new Result(true,"数据删除成功");
        } catch (Exception e) {
            return new Result(false,"数据删除失败");
        }
    }
}
