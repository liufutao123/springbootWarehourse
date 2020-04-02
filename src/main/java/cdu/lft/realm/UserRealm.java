package cdu.lft.realm;

import cdu.lft.bean.Permission;
import cdu.lft.bean.User;
import cdu.lft.common.ActiverUser;
import cdu.lft.common.Constast;
import cdu.lft.service.PermissionService;
import cdu.lft.service.RoleService;
import cdu.lft.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jdk.nashorn.internal.parser.Token;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-28 23:10
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;


    public String getName(){
        return this.getClass().getSimpleName();
    }

    /*
    *认证
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("loginname", token.getPrincipal().toString());
        User user=userService.getOne(queryWrapper);
        if (user!=null){
            ActiverUser activerUser=new ActiverUser();
            activerUser.setUser(user);

            //根据用户id查寻permission
            //第一步：通过用户id拿到用户拥有的角色id
            List<Integer> rids=roleService.queryRoleUserIdsByUid(user.getId());
            //第二步：通过拿到的角色id，查询角色拥有的权限id（及菜单）
            List<Integer> pid=null;
            if(rids.size()>0){
                pid=roleService.queryRolePermissionIdsByRids(rids);
            }else {
                pid=new ArrayList<>();
            }
            //第三步：通过拿到的权限id查询用户可可以使用的权限
            List<Permission> list=null;
            if (pid.size()>0){
                QueryWrapper<Permission> queryWrapper2=new QueryWrapper<>();
                queryWrapper2.eq("type", Constast.TYPE_PERMISSION).eq("available",Constast.AVAILABLE_TRUE);
                queryWrapper2.in("id",pid);
                list=permissionService.list(queryWrapper2);
            }else {
                list=new ArrayList<>();
            }
            //第四步：取出权限码
            List<String> stringList=new ArrayList<>();
            for (Permission permission:list){
                stringList.add(permission.getPercode());
            }
            activerUser.setPermissions(stringList);

            ByteSource byteSource=ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(activerUser,user.getPwd(),byteSource,this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }

    /*
    * 授权
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        ActiverUser activerUser=(ActiverUser) principalCollection.getPrimaryPrincipal();
        User user =activerUser.getUser();
        List<String> permissions=activerUser.getPermissions();
        if(user.getType()==Constast.USER_TYPE_SUPER){
            authorizationInfo.addStringPermission("*:*");
        }else {
            if(null!=permissions&&permissions.size()>0){
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }


}
