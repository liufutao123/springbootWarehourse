package cdu.lft.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-28 23:30
 */
@Controller
@RequestMapping("sys")
public class SystemController {


    /*************登录界面跳转开始******************/
    /*
    * 跳转到登录界面
    * */
    @RequestMapping("toLogin")
    public String toLogin(){
        return "system/index/login";
    }
    /**************登录界面跳转结束*****************/


    /*************首页界面跳转开始******************/
    /*
    * 跳转到首页
    * */
    @RequestMapping("index")
    public String toIndex(){
        return "system/index/index";
    }
    /**************首页界面跳转结束*****************/



    /**************控制台界面跳转开始*****************/
    /*
    * 跳转到控制台
    * */
    @RequestMapping("deskManager")
    public String toDeskManager(){
        return "system/index/deskManager";
    }
    /*************控制台界面跳转结束******************/


    /*************日志管理界面跳转开始******************/
    /*
     * 跳转到日志管理
     * */
    @RequestMapping("toLoginfoManager")
    public String toLoginfoManager(){
        return "system/loginfo/loginfoManager";
    }
    /*************日志管理界面跳转结束******************/


    /*************消息管理界面跳转开始******************/
    /*
    * 跳转到消息管理
    * */
    @RequestMapping("toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/noticeManager";
    }
    /**************消息管理界面跳转结束******************/

    /**************部门管理界面跳转开始*****************/
    /*
     * 跳转到部门管理
     * */
    @RequestMapping("toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    }
    /*
     * 跳转到消息左页面
     * */
    @RequestMapping("toDeptLeft")
    public String toDeptLeft(){
        return "system/dept/deptLeft";
    }
    /*
     * 跳转到消息右页面
     * */
    @RequestMapping("toDeptRight")
    public String toDeptRight(){
        return "system/dept/deptRight";
    }
    /*************部门管理界面跳转结束******************/


    /*************菜单管理界面跳转开始******************/
    /*
     * 跳转到菜单管理
     * */
    @RequestMapping("toMenuManager")
    public String toMenuManager(){
        return "system/menu/menuManager";
    }
    /*
     * 跳转到菜单左页面
     * */
    @RequestMapping("toMenuLeft")
    public String toMenuLeft(){
        return "system/menu/menuLeft";
    }
    /*
     * 跳转到菜单右页面
     * */
    @RequestMapping("toMenuRight")
    public String toMenuRight(){
        return "system/menu/menuRight";
    }
    /*************菜单管理界面跳转结束******************/

    
    /*************权限管理界面跳转开始******************/
    /*
     * 跳转到菜单管理
     * */
    @RequestMapping("toPermissionManager")
    public String toPermissionManager(){
        return "system/permission/permissionManager";
    }
    /*
     * 跳转到菜单左页面
     * */
    @RequestMapping("toPermissionLeft")
    public String toPermissionLeft(){
        return "system/permission/permissionLeft";
    }
    /*
     * 跳转到菜单右页面
     * */
    @RequestMapping("toPermissionRight")
    public String toPermissionRight(){
        return "system/permission/permissionRight";
    }
    /*************权限管理界面跳转结束******************/

    /*************角色管理界面跳转结束******************/
    @RequestMapping("toRoleManager")
    public String toRoleManager(){
        return "system/role/roleManager";
    }
    /*************角色管理界面跳转结束******************/


    /*************用户管理界面跳转开始******************/
    @RequestMapping("toUserManager")
    public String toUserManager(){
        return "system/user/userManager";
    }
    /*************用户管理界面跳转结束******************/


    /*************缓存管理界面跳转开始******************/
    @RequestMapping("toCacheManager")
    public String toCacheManager(){
        return "system/cache/cacheManager";
    }
    /*************缓存管理界面跳转结束******************/

}
