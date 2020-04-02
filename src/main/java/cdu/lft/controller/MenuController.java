package cdu.lft.controller;

import cdu.lft.bean.Permission;
import cdu.lft.bean.User;
import cdu.lft.common.*;
import cdu.lft.service.PermissionService;
import cdu.lft.service.RoleService;
import cdu.lft.vo.PermissionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-29 10:46
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo){
        //查询所有菜单
        QueryWrapper<Permission> wrapper=new QueryWrapper<>();
        wrapper.eq("type", Constast.TYPE_MENU).eq("available",Constast.AVAILABLE_TRUE);
        User user=(User) WebUtils.getSession().getAttribute("user");
        List<Permission> list=null;
        if(user.getType()==Constast.USER_TYPE_SUPER){
            list=permissionService.list(wrapper);
        }else{
            //根据用户id和角色权限去查询
            //第一步：通过用户id拿到用户拥有的角色id
            List<Integer> rids=roleService.queryRoleUserIdsByUid(user.getId());
            //第二步：通过拿到的角色id，查询角色拥有的权限id（及菜单）
            List<Integer> pid=null;
            if (rids.size()>0) {
                pid = roleService.queryRolePermissionIdsByRids(rids);
            } else {
                pid=new ArrayList<>();
            }
            //第三步：通过拿到的权限id查询用户可是使用的权限
            if (pid.size()>0) {
                wrapper.in("id",pid);
                list=permissionService.list(wrapper);
            } else {
                list=new ArrayList<>();
            }
        }

        List<TreeNode> nodeList=new ArrayList<>();
        for (Permission p:list){
            Integer id=p.getId();
            Integer pid=p.getPid();
            String title=p.getTitle();
            String icon=p.getIcon();
            String href=p.getHref();
            Boolean spread=p.getOpen()==Constast.OPEN_TRUE?true:false;
            nodeList.add(new TreeNode(id,pid,title,icon,href,spread));
        }
        List<TreeNode> treeNodeList=TreeNodeBuilder.builder(nodeList,1);

        return new DataGridView(treeNodeList);
    }

/**************菜单管理开始*****************/
    /*
     * 加载菜单管理左边的部门树的json
     * */
    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(){
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",Constast.TYPE_MENU);
        List<Permission> permissionList=permissionService.list(queryWrapper);
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission permission:permissionList){
            Boolean spread=permission.getOpen()==Constast.OPEN_TRUE?true:false;
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /*
     * 查询菜单
     * */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo){
        IPage<Permission> page=new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.eq(permissionVo.getId()!=null,"id",permissionVo.getId()).or().eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        permissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /*
     *查询最大的排序码
     * */
    @RequestMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadMenuMaxOrderNum(){

        Map<String,Object> map=new HashMap<>();
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<Permission> permissionList=permissionService.list(queryWrapper);
        if (permissionList!=null){
            map.put("value",permissionList.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }

    /*
     * 添加菜单
     * */
    @RequestMapping("addMenu")
    public ResultObj addMenu(Permission permission){
        try {
            permission.setType(Constast.TYPE_MENU);
            permissionService.save(permission);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FALSE;
        }
    }

    /*
     * 更新菜单
     * */
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(Permission permission){
        try {
            permissionService.updateById(permission);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FALSE;
        }
    }

    /*
     * 删除菜单
     * */
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(Integer id){

        try {
            permissionService.removeById(id);
            return  ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    /*
     * 批量删除菜单
     * */
    @RequestMapping("batchDeleteMenu")
    public ResultObj batchDeleteMenu(Integer[] ids){
        try {
            permissionService.removeByIds(Arrays.asList(ids));
            return  ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    /*
     * 检查当前部门有没有子菜单
     * */
    @RequestMapping("checkMenuHasChildrenNode")
    public Map<String,Object> checkMenuHasChildrenNode(Integer id){
        Map<String,Object> returnMap=new HashMap<>();

        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        Integer count=permissionService.count(queryWrapper);
        if (count>0){
            returnMap.put("value",true);
        }else {
            returnMap.put("value",false);
        }
        return returnMap;
    }

/**************菜单管理结束*****************/



}
