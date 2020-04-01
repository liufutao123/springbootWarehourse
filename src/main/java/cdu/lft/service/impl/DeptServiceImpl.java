package cdu.lft.service.impl;

import cdu.lft.bean.Dept;
import cdu.lft.dao.DeptMapper;
import cdu.lft.service.DeptService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-30
 */
@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public Dept getOne(Wrapper<Dept> queryWrapper, boolean throwEx) {
        return super.getOne(queryWrapper, throwEx);
    }

    @Override
    public boolean updateById(Dept entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
