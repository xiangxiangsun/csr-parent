package css.security.controller;

import css.security.common.MessageConstant;
import css.security.entity.Dept;
import css.security.entity.Result;
import css.security.service.DeptService;
import org.springframework.web.bind.annotation.*;

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
        return new Result(true,MessageConstant.GET_DEPT_SUCCESS,deptService.buildDeptTreeSelect(depts));
    }

//    @GetMapping(value = "/{deptId}")
//    public Result getInfo(@PathVariable String deptId){
//        return Result.success(MessageConstant.GET_DEPT_SUCCESS,deptService.selectDeptById(deptId));
//    }

    @RequestMapping("/findByDeptId")
    public Result selectDeptById(Long deptId){
        return new Result(true,MessageConstant.GET_DEPT_SUCCESS,deptService.selectDeptById(deptId));
    }

    /**
     * 修改部门
     */
    @PutMapping
    public Result edit(@RequestBody Dept dept){
        if ("1".equals(deptService.checkDeptNameUnique(dept))){
            return new Result(false,MessageConstant.UPDATE_DEPTNAME_NOTUNIQUE_ERROR);
        }else if (dept.getParentId().equals(dept.getDeptId())){
            return new Result(false,MessageConstant.UPDATE_PARENTID_NOTSELF_ERROR);
        }
        return new Result(true,MessageConstant.UPDATE_DEPT_SUCCESS,deptService.updateDept(dept));
    }

    /**
     * 添加部门
     */
    @RequestMapping("/addDept")
    public Result addDept(@RequestBody Dept dept){
        if ("1".equals(deptService.checkDeptNameUnique(dept))){
            return new Result(false,MessageConstant.UPDATE_DEPTNAME_NOTUNIQUE_ERROR);
        }
        return new Result(true,MessageConstant.ADD_DEPT_SUCCESS,deptService.insertDept(dept));
    }

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    @RequestMapping("/deleteDept")
    public Result deleteDept(Long deptId){
        if (deptService.hasChildByDeptId(deptId)){
            return new Result(false,"存在下级部门，不允许删除");
        }
//        if (deptService.checkDeptExistUser(deptId)){
//            return new Result(false,"部门存在用户，不允许删除");
//        }
        return new Result(true,MessageConstant.DELETE_DEPT_SUCCESS,deptService.deleteDept(deptId));
    }

}
