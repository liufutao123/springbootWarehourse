package cdu.lft.controller;


import cdu.lft.bean.Permission;
import cdu.lft.bean.Role;
import cdu.lft.common.Constast;
import cdu.lft.common.DataGridView;
import cdu.lft.common.ResultObj;
import cdu.lft.common.TreeNode;
import cdu.lft.service.PermissionService;
import cdu.lft.service.RoleService;
import cdu.lft.vo.RoleVo;
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
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**************角色管理开始*****************/
    /*
     * 查询角色
     * */
    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){
        IPage<Role> page=new Page<>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark()).or();
        queryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        roleService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /*
     * 添加角色
     * */
    @RequestMapping("addRole")
    public ResultObj addRole(Role role){
        try {
            role.setCreatetime(new Date());
            roleService.save(role);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FALSE;
        }
    }

    /*
     * 更新角色
     * */
    @RequestMapping("updateRole")
    public ResultObj updateRole(Role role){
        try {
            roleService.updateById(role);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FALSE;
        }
    }

    /*
     * 删除角色
     * */
    @RequestMapping("deleteRole")
    public ResultObj deleteRole(Integer id){

        try {
            roleService.removeById(id);
            return  ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

/**************角色管理结束*****************/




/**************角色权限分配管理开始*****************/

    /*
     *根据角色id加载菜单和权限的树的json串
     * */
    @RequestMapping("initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer roleId){
        //查询所有的权限和菜单
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        List<Permission> allPermissions=permissionService.list(queryWrapper);
        //查询当前角色拥有的权限和菜单
        List<Integer> currentRolePermissionIds=roleService.queryRolePermissionIdsByRid(roleId);
    //        queryWrapper.in(currentRolePermissionIds.size()>0,"id",currentRolePermissionIds);
    //        List<Permission> currentRolePermissions=permissionService.list(queryWrapper);

        //构造TreeNode树
        List<TreeNode> nodes=new ArrayList<>();
        for(Permission permission:allPermissions){
            String checkArr="0";
            for (Integer pid:currentRolePermissionIds){
                if (permission.getId()==pid) {
                    checkArr="1";
                    break;
                }
            }
            Boolean spread=permission.getPid()==0?true:false;
            nodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),spread,checkArr));
        }
        return new DataGridView(nodes);
    }

    @RequestMapping("saveRolePermission")
    public ResultObj saveRolePermission(Integer rid,Integer[] ids){
        try {
            roleService.saveRolePermission(rid,ids);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FALSE;
        }
    }


/**************角色权限分配管理结束*****************/
}

