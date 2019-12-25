package css.security.controller;

import css.security.common.MessageConstant;
import css.security.entity.Dept;
import css.security.entity.Result;
import css.security.service.DeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;

    /**
     *  获取所有部门
     */
    @RequestMapping("/getAll")
    public Result getAll(){
        List<Dept> depts = deptService.findTree();
        return Result.success(MessageConstant.GET_DEPT_SUCCESS,depts);
    }

    /**
     *  查询部门下拉树结构
     */
    @RequestMapping("/treeSelect")
    public Result treeSelect(){
        List<Dept> depts = deptService.findTree();
        return Result.success(MessageConstant.GET_DEPT_SUCCESS,deptService.buildDeptTreeSelect(depts));
    }

//    @GetMapping(value = "/{deptId}")
//    public Result getInfo(@PathVariable String deptId){
//        return Result.success(MessageConstant.GET_DEPT_SUCCESS,deptService.selectDeptById(deptId));
//    }

    @RequestMapping("/findByDeptId")
    public Result selectDeptById(Integer deptId){
        return Result.success(MessageConstant.GET_DEPT_SUCCESS,deptService.selectDeptById(deptId));
    }

}
