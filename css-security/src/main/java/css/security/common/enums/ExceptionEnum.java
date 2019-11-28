package css.security.common.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    /**
     * 注释
     */
    PRICE_CANNOT_BE_NULL(400,"价格不能为空!"),
    DATA_TRANSFER_ERROR(500,"后台数据转换过程出错"),
    INSERT_OPTIONS_ERROR(500,"数据保存失败")
    ;

    private int status;
    private String message;

    ExceptionEnum(int status, String message){
        this.status = status;
        this.message = message;
    }
}
