package cdu.lft.controller;

import cdu.lft.bean.Loginfo;
import cdu.lft.common.ActiverUser;
import cdu.lft.common.ResultObj;
import cdu.lft.common.WebUtils;
import cdu.lft.service.LoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-28 23:38
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginfoService loginfoService;

    @RequestMapping("/login")
    public ResultObj login(String loginname, String pwd){
        Subject subject=SecurityUtils.getSubject();
        AuthenticationToken token=new UsernamePasswordToken(loginname,pwd);
        try{
            subject.login(token);
            ActiverUser activerUser=(ActiverUser)subject.getPrincipal();
            WebUtils.getSession().setAttribute("user",activerUser.getUser());
            Loginfo loginfo=new Loginfo();
            loginfo.setLoginname(activerUser.getUser().getName()+"-"+activerUser.getUser().getLoginname());
            loginfo.setLogintime(new Date());
            loginfo.setLoginip(WebUtils.getRequest().getRemoteAddr());
            loginfoService.save(loginfo);
            return ResultObj.LOGIN_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.LOGIN_ERROR_PASS;
        }
    }
}
