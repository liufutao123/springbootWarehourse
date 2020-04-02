package cdu.lft.dao;

import cdu.lft.bean.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-31
 */
public interface RoleMapper extends BaseMapper<Role> {

    /*
    * 通过角色id查询所有的权限id
    * */
    List<Integer> queryRolePermissionIdsByRid(Integer rid);

    /*
     *根据权限或菜单id删除权限和角色的关系表中的数据
     * */
    Integer deleteRolePermissionByRid(Integer rid);

    /*
    * 添加角色权限
    * */
    Integer saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);


    /*
    * 通过角色号list查询出所有权限号
    * */
    List<Integer> queryRolePermissionIdsByRids(List<Integer> ids);


    /*
     * 通过用户id查询所有的角色id
     * */
    List<Integer> queryRoleUserIdsByUid(Integer uid);


    /*
     *根据用户id删除用户和角色的关系表中的数据
     * */
    Integer deleteRoleUserByUid(Integer uid);

    /*
     * 添加用户=角色
     * */
    Integer saveRoleUser(@Param("uid") Integer uid, @Param("rid") Integer rid);




}
