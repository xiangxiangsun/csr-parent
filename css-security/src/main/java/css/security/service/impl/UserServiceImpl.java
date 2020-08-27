package css.security.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import css.security.common.enums.ExceptionEnum;
import css.security.common.exception.CssException;
import css.security.dao.PermissionDao;
import css.security.dao.RoleDao;
import css.security.dao.UserDao;
import css.security.dto.SysUserDTO;
import css.security.entity.*;
import css.security.service.UserService;
import css.security.utils.BeanHelper;
import css.security.utils.JuheSmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

@Component
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Resource
    RoleDao roleDao;

    @Resource
    PermissionDao permissionDao;

    @Override
    public SysUser loadUserByUsername(String username) {
        //根据用户名查询用户信息
        SysUser user = userDao.findByUserName(username);
        //根据用户信息查询用户所有的角色
        if(null != user){
            Set<SysRole> roles = roleDao.findRolesByUserId(user.getId());
            if(null != roles && roles.size() > 0){
                for (SysRole role : roles) {
                    //根据角色查询角色对应的权限
                    Set<Permission> permissions = permissionDao.findPermissionsByRoleId(role.getId());
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public PageResult<SysUserDTO> findPage(SysUserDTO userDTO) {
        //分页
        PageHelper.startPage(userDTO.getCurrentPage(),userDTO.getPageSize());
        List<SysUser> userList = new ArrayList<>() ;
        if (userDTO.getDeptid() != null){
            //判断是否是根节点
            List<Integer> userListByDept = userDao.selectUserListByDept(userDTO.getDeptid());
            for (Integer userIdByDept : userListByDept) {
                userList.add(userDao.findById(userIdByDept));
            }
        }else {
            userList = userDao.selectUserList(userDTO);
        }
/*        if(CollectionUtils.isEmpty(userList)){
            throw new CssException(ExceptionEnum.INSERT_OPTIONS_ERROR);
        }*/
        //解析分页结果
        //自动计算：总元素个数，以及总页数
        PageInfo<SysUser> pageInfo = new PageInfo<>(userList);
//        System.out.println("查询到数据："+userList.size());
//        System.out.println("总个数："+pageInfo.getTotal());

/*        for (Iterator<SysUser> it = userList.iterator();it.hasNext();){
            SysUser deptUser = it.next();
            if (deptUser.getDeptid() != null){
                Dept dept = userDao.selectByDeptId(deptUser.getDeptid());
                deptUser.setDept(dept);
            }
        }*/
        //转为SysUserDTO,减少流量传输
        List<SysUserDTO> list = BeanHelper.copyWithCollection(userList,SysUserDTO.class);
        //返回：个数，总页数，当前页数据
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getPages(),list);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void add(UserTable user) {
        //初始密码默认123456，并用盐值加密
//        String password = passwordEncoder.encode("123456");
        //先将用户基本信息进行添加
        userDao.add(user.getSysUser());
        //通过uesrIds修改角色信息
        setUserAndRole(user.getSysUser().getId(),user.getRoleIds());
        //通过uesrIds修改部门信息
        setUserAndDept(user.getSysUser().getId(),user.getDeptIds());
    }

    @Override
    public SysUser findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer id) {
        return userDao.findRoleIdsByUserId(id);
    }

    @Override
    public void edit(UserTable user) {
        //先将用户基本信息进行修改
        userDao.edit(user.getSysUser());
        //通过userId对已有角色中间表进行删除
        userDao.deleteRoleIdByUserId(user.getSysUser().getId());
        //再次添加
        setUserAndRole(user.getSysUser().getId(),user.getRoleIds());
        //通过userId对已有部门中间表进行删除
        userDao.deleteDeptIdByUserId(user.getSysUser().getId());
        //再次添加
        setUserAndDept(user.getSysUser().getId(),user.getDeptIds());
    }

    @Override
    public void deleteById(Integer id) {
        //先删除中价表关系
        userDao.deleteRoleIdByUserId(id);
        userDao.deleteDeptIdByUserId(id);
        //再删除user表基本信息
        userDao.deleteByUserId(id);
    }

    @Override
    public List<Integer> findDeptIdsByUserId(Integer id) {
        return userDao.findDeptIdsByUserId(id);
    }

    public void setUserAndRole(Integer userId, Integer[] roleIds) {
        if (roleIds != null && roleIds.length>0){
            for (Integer roleId : roleIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("user_id",userId);
                map.put("role_id",roleId);
                userDao.setUserAndRole(map);
            }
        }
    }

    public void setUserAndDept(Integer userId, Integer[] deptIds){
        if (deptIds != null && deptIds.length>0){
            for (Integer deptId : deptIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("user_id",userId);
                map.put("dept_id",deptId);
                userDao.setUserAndDept(map);
            }
        }
    }

    @Scheduled(cron="0 0 0 ? * MON-FRI")
    public void job(){
        System.out.println(DateUtil.date());
        JuheSmsUtils.sendTz("13671541479");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int i = cal.get(Calendar.DAY_OF_WEEK);
        if (6 == i) {
            JuheSmsUtils.sendTz("18961748589");
        }

    }

}
