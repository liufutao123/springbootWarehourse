package cdu.lft.common;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-28 23:45
 */
public interface Constast {
    /*
    * 状态码
    * */
    public static final  Integer OK=200;
    public static final  Integer ERROR=-1;

    /*
    * 用户默认密码
    * */
    public static final String USER_DEFAULT_PWD="123456";

    /*
    * 菜单权限类型
    * */

    public static final String TYPE_MENU="menu";
    public static final String TYPE_PERMISSION="permission";

    /*
    * 可用状态
    * */

    public static  final int AVAILABLE_FALSE=0;
    public static  final int AVAILABLE_TRUE=1;

    /*
    * 用户类型
    * */

    public static  final  Integer USER_TYPE_SUPER=0;
    public static  final  Integer USER_TYPE_NORMAL=1;

    /*
    * 展开类型
    * */
    public static  final  Integer OPEN_TRUE=1;
    public static  final  Integer OPEN_FALSE=0;



}
