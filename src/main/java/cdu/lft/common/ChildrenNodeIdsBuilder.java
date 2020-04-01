package cdu.lft.common;

import cdu.lft.bean.Dept;
import cdu.lft.bean.Permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-31 15:04
 */
public class ChildrenNodeIdsBuilder {

    public static List<Integer> deptBuilder(List<Dept> list){

        List<Integer> ids=new ArrayList<>();
        if(list!=null){
            for(Dept dept:list){
                ids.add(dept.getPid());
            }
        }else {
            return null;
        }
        return ids;
    }

    public static List<Permission> perssionBuilder(List<Permission> list,Integer id){
        List<Permission> permissionList=new ArrayList<>();
        if(id!=null){
            for(Permission p1:list){
                if (p1.getPid()==id){
                    permissionList.add(p1);
                    for (Permission p2:list){
                        if (p2.getPid()==p1.getId()){
                            permissionList.add(p2);
                            for (Permission p3:list){
                                if (p3.getPid()==p2.getId()&&p3.getType().equals(Constast.TYPE_PERMISSION)){
                                    permissionList.add(p3);
                                }
                            }
                        }
                    }
                }
            }
            Collections.sort(permissionList);
            return permissionList;
        }else {
            return list;
        }

    }

}
