package cdu.lft.vo;

import cdu.lft.bean.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-29 10:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginfoVo extends Loginfo {

    private static final long serialVersionUID=1L;

    private Integer page;
    private Integer limit;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;



}
