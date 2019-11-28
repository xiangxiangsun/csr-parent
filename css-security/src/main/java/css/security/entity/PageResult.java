package css.security.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */
@Data
public class PageResult<T>{
    private Long total;//总记录数
    private Integer totalPage;//总页数
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total,List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult(Long total,Integer totalPage,List<T> rows) {
        this.total = total;
        this.totalPage = totalPage;
        this.rows = rows;
    }
}
