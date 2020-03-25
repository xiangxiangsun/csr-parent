package css.security.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@TableName(value = "public.t_user_role")
public class SysUserRole implements Serializable {

    @TableId(value = "user_id")
    private Integer userId;

    private Integer roleId;

}
