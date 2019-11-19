package csr.security.dao;

import csr.security.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UpdatePwdDao {

    //确认密码，password为旧密码
    SysUser ConfirmPassword(@Param("username")String username,@Param("password")String pwd);

    //修改密码，password为新密码
    void updatePwd(@Param("username")String username,@Param("password")String pwd);
}
