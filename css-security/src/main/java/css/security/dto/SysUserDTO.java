package css.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import css.security.entity.Dept;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserDTO {

    private Integer id; // 主键

    private String username; // 用户名，唯一

    private String gender; // 性别

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday; // 生日

    private String remark; // 备注

    private String station; // 状态

    private String telephone; // 联系电话

    private Integer deptid; //Tree 部门Id 查询方式

    private Dept dept;

    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件



}
