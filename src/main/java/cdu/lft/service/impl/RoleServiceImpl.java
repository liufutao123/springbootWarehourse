package cdu.lft.service.impl;

import cdu.lft.bean.Role;
import cdu.lft.dao.RoleMapper;
import cdu.lft.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
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
        if (ids!=null){
            for (Integer pid:ids){
                roleMapper.saveRolePermission(id,pid);
            }
        }

    }


    public  List<Integer> queryRolePermissionIdsByRids(List<Integer> ids){
        System.out.println(ids);
        RoleMapper roleMapper=this.getBaseMapper();
        List<Integer> list=roleMapper.queryRolePermissionIdsByRids(ids);
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }




    public List<Integer> queryRoleUserIdsByUid(Integer uid){
        RoleMapper roleMapper=this.getBaseMapper();
        return roleMapper.queryRoleUserIdsByUid(uid);
    }

    public void saveUserRole(Integer uid,Integer[] ids){
        RoleMapper roleMapper=this.getBaseMapper();
        roleMapper.deleteRoleUserByUid(uid);
        if(ids!=null){
            for (Integer rid:ids){
                roleMapper.saveRoleUser(uid,rid);
            }
        }

    }


}
