package cdu.lft.sys.controller;


import cdu.lft.sys.bean.Dept;
import cdu.lft.sys.bean.Role;
import cdu.lft.sys.bean.User;
import cdu.lft.common.*;
import cdu.lft.sys.service.DeptService;
import cdu.lft.sys.service.RoleService;
import cdu.lft.sys.service.UserService;
import cdu.lft.sys.vo.UserVo;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-28
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;


    /*
    * 用户全查询
    * */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){
        IPage<User> page=new Page<>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName()).or().like(StringUtils.isNotBlank(userVo.getName()),"loginname",userVo.getName());
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());

        userService.page(page,queryWrapper);

        List<User> userList=page.getRecords();
        for (User user:userList){
            Integer deptid=user.getDeptid();
            if (deptid!=null){
                Dept dept=deptService.getById(deptid);
                user.setDeptname(dept.getTitle());
            }
            Integer mgr=user.getMgr();
            if (mgr!=null){
                User one=userService.getById(mgr);
                user.setLeadername(one.getName());
            }
        }
    return new DataGridView(page.getTotal(),userList);
    }


    /*
    *查询排序吗
    * */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String, Integer> loadUserMaxOrderNum(){
        Map<String,Integer> map=new HashMap<>();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<User> userList=userService.list(queryWrapper);
        if (userList!=null){
            map.put("value",userList.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }


    /*
    *通过部门号查询用户
    * */
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptid){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("deptid",deptid);
        queryWrapper.eq("type",Constast.USER_TYPE_NORMAL);
        List<User> list=userService.list(queryWrapper);
        return new DataGridView(list);
    }

    /*
    *添加新用户
    * */
    @RequestMapping("addUser")
    public ResultObj addUser(User user){
        try {
            //设置用户创建时间
            user.setHiredate(new Date());
            //设置用户类型
            user.setType(Constast.USER_TYPE_NORMAL);
            //设置盐
            String salt= IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);
            //设置密码
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            userService.save(user);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FALSE;
        }
    }

    /*
     *修改用户
     * */
    @RequestMapping("updateUser")
    public ResultObj updateUser(User user){
        try {
            userService.updateById(user);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FALSE;
        }
    }

    /*
     *删除用户
     * */
    @RequestMapping("deleteUser")
    public ResultObj deleteUser(Integer id){
        try {
            userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    /*
    * 将用户名转化为拼音
    * */
    @RequestMapping("changeChineseToPinyin")
    public Map<String,String> changeChineseToPinyin(String username){
        Map<String,String> map=new HashMap<>();
        String value=PinyinUtils.getPingYin(username);
        map.put("value",value);
        return map;
    }

    /*
    * 加载领导通过id
    * */
    @RequestMapping("loadUserById")
    public DataGridView loadUserById(Integer id){

        User user=userService.getById(id);
        return new DataGridView(user);
    }


    /*
    * 密码重置
    * */
    @RequestMapping("resetPwd")
    public ResultObj resetPwd(Integer id){
        try {
            User user=new User();
            user.setId(id);
            //设置盐
            String salt= IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);
            //设置密码
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESET_FALSE;
        }
    }

    /*
    * 初始化角色分配表
    * */
    @RequestMapping("initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        //查询出所有角色
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        List<Map<String,Object>> roleList=roleService.listMaps(queryWrapper);
        //查询该用户拥有的角色
        List<Integer> roleIds=roleService.queryRoleUserIdsByUid(id);

        for (Map<String,Object> map:roleList){
            System.out.println(map);
            Boolean LAY_CHECKED=false;
            Integer roleId=(Integer)map.get("id");
            for (Integer rid:roleIds){
                if(rid==roleId){
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }
        return new DataGridView(Long.valueOf(roleList.size()),roleList);
    }

    /*
    * 修改用户角色
    * */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer uid,Integer[] ids){
        try {
            roleService.saveUserRole(uid,ids);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FALSE;
        }
    }



}

