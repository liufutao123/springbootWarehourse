package cdu.lft.vo;

import cdu.lft.bean.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-29 10:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {

    private static final long serialVersionUID=1L;

    private Integer page;
    private Integer limit;



}
