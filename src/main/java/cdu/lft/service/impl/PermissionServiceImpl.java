package cdu.lft.service.impl;

import cdu.lft.bean.Permission;
import cdu.lft.dao.PermissionMapper;
import cdu.lft.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-29
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    /*
    *删除权限的同时删除该权限与角色的关联
    * */
    @Override
    public boolean removeById(Serializable id) {
        PermissionMapper permissionMapper=this.getBaseMapper();
        permissionMapper.deleteRolePermissionByPid((Integer) id);
        return super.removeById(id);
    }

}
