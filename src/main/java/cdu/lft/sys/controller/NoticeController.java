package cdu.lft.sys.controller;


import cdu.lft.sys.bean.Notice;
import cdu.lft.sys.bean.User;
import cdu.lft.common.DataGridView;
import cdu.lft.common.ResultObj;
import cdu.lft.common.WebUtils;
import cdu.lft.sys.service.NoticeService;
import cdu.lft.sys.vo.NoticeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-30
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    public NoticeService noticeService;


    /*
    *查询
    * */
    @RequestMapping("loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo){
        IPage<Notice> page=new Page(noticeVo.getPage(),noticeVo.getLimit());
        QueryWrapper<Notice> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()),"opername",noticeVo.getOpername());
        queryWrapper.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        queryWrapper.le(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        noticeService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /*
    * 删除
    * */
    @RequestMapping("deleteNotice")
    public ResultObj deletNotice(Integer id){
        try {
            noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    /*
     * 批量删除
     * */
    @RequestMapping("batchDeleteNotice")
    public ResultObj batchDeleteNotice(Integer[] ids){
        try {
            noticeService.removeByIds(Arrays.asList(ids));
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_FALSE;
        }
    }

    @RequestMapping("addNotice")
    public ResultObj addNotice(Notice notice){
        try {
            User user=(User)WebUtils.getSession().getAttribute("user");
            notice.setOpername(user.getName());
            notice.setCreatetime(new Date());
            noticeService.saveOrUpdate(notice);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_FALSE;
        }
    }

    @RequestMapping("updateNotice")
    public ResultObj updateNotice(Notice notice){
        try {
            noticeService.updateById(notice);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_FALSE;
        }
    }
}

