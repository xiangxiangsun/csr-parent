package css.security.common.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    /**
     * 注释
     */
    DATA_TRANSFER_ERROR(500,"后台数据转换过程出错"),
    INSERT_OPTIONS_ERROR(500,"数据查询失败"),
    INSERT_DEPT_ERROR(500,"部门已经停用，不允许添加"),
    GET_USERNAME_ERROR(500,"获取用户名称异常"),
    GET_USER_ERROR(500,"获取用户信息异常")
    ;


    private int status;
    private String message;

    ExceptionEnum(int status, String message){
        this.status = status;
        this.message = message;
    }
}
