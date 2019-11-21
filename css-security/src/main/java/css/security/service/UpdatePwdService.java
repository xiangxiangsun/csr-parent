package css.security.service;

import css.security.entity.SysUser;

public interface UpdatePwdService {

    SysUser ConfirmPassword(String username,String password);

    void updatePwd(String username,String pwd);

}
