package csr.security.service.impl;

import csr.security.dao.UpdatePwdDao;
import csr.security.entity.SysUser;
import csr.security.service.UpdatePwdService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Component
@Transactional
public class UpdatePwdServiceImpl implements UpdatePwdService{

    @Resource
    UpdatePwdDao updatePwdDao;

    @Override
    public SysUser ConfirmPassword(String username,String password) {
        return updatePwdDao.ConfirmPassword(username,password);
    }

    @Override
    public void updatePwd(String username,String pwd) {
        updatePwdDao.updatePwd(username,pwd);
    }
}
