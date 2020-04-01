package cdu.lft.service.impl;

import cdu.lft.bean.Role;
import cdu.lft.dao.RoleMapper;
import cdu.lft.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-31
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {



    public List<Integer> queryRolePermissionIdsByRid(Integer id){
        RoleMapper roleMapper=this.getBaseMapper();
        return roleMapper.queryRolePermissionIdsByRid(id);
    }

    public void saveRolePermission(Integer id,Integer[] ids){
        RoleMapper roleMapper=this.getBaseMapper();
        roleMapper.deleteRolePermissionByRid(id);
        for (Integer pid:ids){
             roleMapper.saveRolePermission(id,pid);
        }
    }
}
