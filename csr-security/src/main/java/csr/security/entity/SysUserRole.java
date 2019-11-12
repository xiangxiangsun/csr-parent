package csr.security.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@TableName(value = "public.user_role")
public class SysUserRole implements Serializable {
    static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
