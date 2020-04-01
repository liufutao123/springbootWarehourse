package cdu.lft.realm;

import cdu.lft.bean.User;
import cdu.lft.common.ActiverUser;
import cdu.lft.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jdk.nashorn.internal.parser.Token;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-28 23:10
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

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
            ByteSource byteSource=ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(activerUser,user.getPwd(),byteSource,this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


}
