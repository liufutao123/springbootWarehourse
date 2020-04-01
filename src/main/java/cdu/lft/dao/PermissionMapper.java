package cdu.lft.dao;

import cdu.lft.bean.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘福桃
 * @since 2020-03-29
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /*
    *根据权限或菜单id删除权限和角色的关系表中的数据
    * */
    Boolean deleteRolePermissionByPid(Integer pid);



}
