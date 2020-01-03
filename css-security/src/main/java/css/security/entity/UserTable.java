package css.security.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserTable implements Serializable {
    private SysUser sysUser;
    private Integer[] deptIds;//对应部门集合
    private Integer[] roleIds;//对应角色集合
}
