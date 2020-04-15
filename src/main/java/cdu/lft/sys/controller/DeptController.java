package cdu.lft.sys.controller;


import cdu.lft.sys.bean.Dept;
import cdu.lft.common.Constast;
import cdu.lft.common.DataGridView;
import cdu.lft.common.ResultObj;
import cdu.lft.common.TreeNode;
import cdu.lft.sys.service.DeptService;
import cdu.lft.sys.vo.DeptVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-30
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /*
    * 加载部门管理左边的部门树的json
    * */
    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(){

        List<Dept> deptList=deptService.list();
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Dept dept:deptList){
            Boolean spread=dept.getOpen()==Constast.OPEN_TRUE?true:false;
            treeNodes.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),spread));
        }
        return new DataGridView(treeNodes);
    }

    /*
    * 查询部门
    * */
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo){
        IPage<Dept> page=new Page<>(deptVo.getPage(),deptVo.getLimit());
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());
        queryWrapper.eq(deptVo.getId()!=null,"id",deptVo.getId()).or().eq(deptVo.getId()!=null,"pid",deptVo.getId());
        deptService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /*
    *查询最大的排序码
    * */
    @RequestMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){

        Map<String,Object> map=new HashMap<>();
        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        List<Dept> deptList=deptService.list(queryWrapper);
        if (deptList!=null){
            map.put("value",deptList.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }

    /*
    * 添加部门
    * */
    @RequestMapping("addDept")
    public ResultObj addDept(Dept dept){
        try {
            deptService.save(dept);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FALSE;
        }
    }

    /*
    * 更新部门
    * */
    @RequestMapping("updateDept")
    public ResultObj updateDept(Dept dept){
        try {
            deptService.updateById(dept);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FALSE;
        }
    }

    /*
    * 删除部门
    * */
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(Integer id){

        try {
            deptService.removeById(id);
            return  ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    /*
     * 批量删除部门
     * */
    @RequestMapping("batchDeleteDept")
    public ResultObj batchDeleteDept(Integer[] ids){
        try {
            deptService.removeByIds(Arrays.asList(ids));
            return  ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    /*
    * 检查当前部门有没有子部门
    * */
    @RequestMapping("checkDeptHasChildrenNode")
    public Map<String,Object> checkDeptHasChildrenNode(Integer id){
        Map<String,Object> returnMap=new HashMap<>();

        QueryWrapper<Dept> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("pid",id);
        Integer count=deptService.count(queryWrapper);
        if (count>0){
            returnMap.put("value",true);
        }else {
            returnMap.put("value",false);
        }
        return returnMap;
    }

}

