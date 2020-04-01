package cdu.lft.controller;


import cdu.lft.bean.Loginfo;
import cdu.lft.common.DataGridView;
import cdu.lft.common.ResultObj;
import cdu.lft.service.LoginfoService;
import cdu.lft.vo.LoginfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.sql.Wrapper;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-30
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {
    @Autowired
    private LoginfoService loginfoService;

    @RequestMapping("loadAllLoginfo")
    public DataGridView loadAllLoginfo(LoginfoVo loginfoVo){
        IPage<Loginfo> page=new Page(loginfoVo.getPage(),loginfoVo.getLimit());
        QueryWrapper<Loginfo> wrapper=new QueryWrapper<Loginfo>();
        wrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        wrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        wrapper.ge(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        wrapper.le(loginfoVo.getEndTime()!=null,"logintime",loginfoVo.getEndTime());
        wrapper.orderByDesc("logintime");

        loginfoService.page(page,wrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /*
    * 删除
    * */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id){
        try {
            loginfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    /*
    * 批量删除
    * */
    @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo(Integer[] ids){
        try {
            loginfoService.removeByIds(Arrays.asList(ids));
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }


}





