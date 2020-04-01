package cdu.lft.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-28 23:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {
    /*
    * 登录
    * */
    public static  final ResultObj LOGIN_SUCCESS=new ResultObj(Constast.OK,"登录成功");
    public static  final ResultObj LOGIN_ERROR_PASS=new ResultObj(Constast.ERROR,"登录失败,用户名或密码不正确");
    public static  final ResultObj LOGIN_ERROR_CODE=new ResultObj(Constast.ERROR,"登录失败，验证码不正确");

    /*
    * 删除
    * */
    public static  final ResultObj DELETE_SUCCESS=new ResultObj(Constast.OK,"删除成功");
    public static  final ResultObj DELETE_FALSE=new ResultObj(Constast.ERROR,"删除失败");

    /*
    * 添加
    * */
    public static  final ResultObj ADD_SUCCESS=new ResultObj(Constast.OK,"添加成功");
    public static  final ResultObj ADD_FALSE=new ResultObj(Constast.ERROR,"添加失败");

    /*
    * 更新
    * */
    public static  final ResultObj UPDATE_SUCCESS=new ResultObj(Constast.OK,"更新成功");
    public static  final ResultObj UPDATE_FALSE=new ResultObj(Constast.ERROR,"更新失败");

    /*
     *重置
     * */
    public static  final ResultObj RESET_SUCCESS=new ResultObj(Constast.OK,"重置成功");
    public static  final ResultObj RESET_FALSE=new ResultObj(Constast.ERROR,"重置失败");

    /*
     *分配
     * */
    public static  final ResultObj DISPATCH_SUCCESS=new ResultObj(Constast.OK,"分配成功");
    public static  final ResultObj DISPATCH_FALSE=new ResultObj(Constast.ERROR,"分配失败");



    private Integer code;
    private String msg;
}
