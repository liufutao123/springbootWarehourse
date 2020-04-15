package cdu.lft.sys.controller;


import cdu.lft.sys.bean.Permission;
import cdu.lft.common.*;
import cdu.lft.sys.service.PermissionService;
import cdu.lft.sys.vo.PermissionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-29
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /*
     * 加载部门管理左边的部门树的json
     * */
    @RequestMapping("loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(){
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type",Constast.TYPE_MENU);
        List<Permission> permissionList=permissionService.list(queryWrapper);
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission permission:permissionList){
            Boolean spread= null;
            if (permission.getPid()==0) {
                spread = true;
            }else {
                spread=false;
            }
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /*
     * 查询权限
     * */
    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo){
        IPage<Permission> page=new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getPercode()),"percode",permissionVo.getPercode());
        queryWrapper.eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        queryWrapper.eq("type",Constast.TYPE_PERMISSION);
        queryWrapper.orderByAsc("ordernum");
        permissionService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /*
     *查询最大的排序码
     * */
    @RequestMapping("loadPermissionMaxOrderNum")
    public Map<String,Object> loadPermissionMaxOrderNum(){
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
     * 添加权限
     * */
    @RequestMapping("addPermission")
    public ResultObj addPermission(Permission permission){
        try {
            permission.setType(Constast.TYPE_PERMISSION);
            permissionService.save(permission);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FALSE;
        }
    }

    /*
     * 更新权限
     * */
    @RequestMapping("updatePermission")
    public ResultObj updatePermission(Permission permission){
        try {
            permissionService.updateById(permission);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FALSE;
        }
    }

    /*
     * 删除权限
     * */
    @RequestMapping("deletePermission")
    public ResultObj deletePermission(Integer id){

        try {
            permissionService.removeById(id);
            return  ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    /*
     * 批量删除权限
     * */
    @RequestMapping("batchDeletePermission")
    public ResultObj batchDeletePermission(Integer[] ids){
        try {
            permissionService.removeByIds(Arrays.asList(ids));
            return  ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

}

