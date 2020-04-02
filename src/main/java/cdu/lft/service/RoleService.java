package cdu.lft.service;

import cdu.lft.bean.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-31
 */
public interface RoleService extends IService<Role> {

    List<Integer> queryRolePermissionIdsByRid(Integer id);

    void saveRolePermission(Integer id,Integer[] ids);

    List<Integer> queryRolePermissionIdsByRids(List<Integer> ids);




    List<Integer> queryRoleUserIdsByUid(Integer uid);

    void saveUserRole(Integer uid,Integer[] ids);
}
