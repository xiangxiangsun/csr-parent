package css.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class SysUserDTO {

    private Integer id; // 主键

    private String username; // 用户名，唯一

    private String password; // 密码

    private String gender; // 性别

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday; // 生日

    private String remark; // 备注

    private String station; // 状态

    private String telephone; // 联系电话

}
