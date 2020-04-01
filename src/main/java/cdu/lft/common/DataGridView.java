package cdu.lft.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * json数据实体
 * @author 刘 福桃
 * TODO：
 * date 2020-03-29 10:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataGridView {

    private Integer code=0;
    private String msg="";
    private Long count=0L;
    private Object data;

    public DataGridView(Long count, Object data) {
        this.count = count;
        this.data = data;
    }

    public DataGridView(Object data) {
        this.data = data;
    }
}
