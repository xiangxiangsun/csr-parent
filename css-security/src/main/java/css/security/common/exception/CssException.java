package css.security.common.exception;

import css.security.common.enums.ExceptionEnum;
import lombok.Getter;

@Getter
public class CssException extends RuntimeException {
    private int status;

    public CssException(ExceptionEnum em){
        super(em.getMessage());
        this.status = em.getStatus();
    }

    public CssException(ExceptionEnum em, Throwable cause){
        super(em.getMessage(),cause);
        this.status = em.getStatus();
    }
}
