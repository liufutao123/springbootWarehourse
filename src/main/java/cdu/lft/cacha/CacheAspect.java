package cdu.lft.cacha;

import cdu.lft.bean.Dept;
import cdu.lft.bean.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-31 02:12
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {

    /*
    * 日志出处
    * */
    private Log log= LogFactory.getLog(CacheAspect.class);

    //声明一个缓存容器
    private static Map<String,Object>  CACHE_CONTAINER=new HashMap<>();

    //声明切面表达式
    private static final String POINTCUT_DEPT_UPDATE="execution(* cdu.lft.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GETBYID="execution(* cdu.lft.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINTCUT_DEPT_REMOVE="execution(* cdu.lft.service.impl.DeptServiceImpl.removeById(..))";
    private static final String POINTCUT_DEPT_SAVE="execution(* cdu.lft.service.impl.DeptServiceImpl.save(..))";


    private static final String POINTCUT_USER_UPDATE="execution(* cdu.lft.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_GETBYID="execution(* cdu.lft.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_REMOVE="execution(* cdu.lft.service.impl.UserServiceImpl.removeById(..))";
    private static final String POINTCUT_USER_SAVE="execution(* cdu.lft.service.impl.UserServiceImpl.save(..))";



    private static final String CACHE_DEPT_PROFIX="dept:";
    private static final String CACHE_USER_PROFIX="user:";



    /*************************部门切面处理开始*************************************/

    /*
    * 查询切入
    * */
    @Around(value = POINTCUT_DEPT_GETBYID)
    public Dept cacheDeptGetOne(ProceedingJoinPoint joinPoint) throws Throwable {

        //取出第一个参数
        Integer object=(Integer)joinPoint.getArgs()[0];

        Dept result1=(Dept)CACHE_CONTAINER.get(CACHE_DEPT_PROFIX+object);
        if (result1!=null){
            log.info("从缓存中获取部门对象:  "+object);
            return result1;
        }else {
            Dept result2=(Dept)joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+object,result2);
            log.info("从数据库中获取部门对象:  "+object);
            return  result2;
        }
    }

    /*
     * 更新切入
     * */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Boolean cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Dept object=(Dept)joinPoint.getArgs()[0];
        Boolean isSuccess=(Boolean)joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX+object.getId());
            return true;
        }else {
            return  false;
        }
    }

    /*
     * 删除切入
     * */
    @Around(value = POINTCUT_DEPT_REMOVE)
    public Boolean cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object=(Integer) joinPoint.getArgs()[0];
        Boolean isSuccess=(Boolean)joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX+object);
            return true;
        }else {
            return  false;
        }
    }

    /*
     * 保存切入
     * */
    @Around(value = POINTCUT_DEPT_SAVE)
    public Boolean cacheDeptSave(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Dept object=(Dept) joinPoint.getArgs()[0];
        Boolean isSuccess=(Boolean)joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+object.getId(),object);
            return true;
        }else {
            return  false;
        }
    }
    /*************************部门切面处理结束*************************************/


    /*************************用户切面处理开始*************************************/
    /*
     * 查询切入
     * */
    @Around(value = POINTCUT_USER_GETBYID)
    public User cacheUserGetOne(ProceedingJoinPoint joinPoint) throws Throwable {

        //取出第一个参数
        Integer object=(Integer)joinPoint.getArgs()[0];

        User result1=(User)CACHE_CONTAINER.get(CACHE_USER_PROFIX+object);
        if (result1!=null){
            log.info("从缓存中获取用户对象:  "+object);
            return result1;
        }else {
            User result2=(User)joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_USER_PROFIX+object,result2);
            log.info("从数据库中获取用户对象:  "+object);
            return  result2;
        }
    }

    /*
     * 更新切入
     * */
    @Around(value = POINTCUT_USER_UPDATE)
    public Boolean cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        User object=(User)joinPoint.getArgs()[0];
        Boolean isSuccess=(Boolean)joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTAINER.remove(CACHE_USER_PROFIX+object.getId());
            return true;
        }else {
            return  false;
        }
    }

    /*
     * 删除切入
     * */
    @Around(value = POINTCUT_USER_REMOVE)
    public Boolean cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object=(Integer) joinPoint.getArgs()[0];
        Boolean isSuccess=(Boolean)joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTAINER.remove(CACHE_USER_PROFIX+object);
            return true;
        }else {
            return  false;
        }
    }

    /*
     * 保存切入
     * */
    @Around(value = POINTCUT_USER_SAVE)
    public Boolean cacheUserSave(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        User object=(User) joinPoint.getArgs()[0];
        Boolean isSuccess=(Boolean)joinPoint.proceed();
        if (isSuccess){
            CACHE_CONTAINER.put(CACHE_USER_PROFIX+object.getId(),object);
            return true;
        }else {
            return  false;
        }
    }
    /*************************用户切面处理结束*************************************/


  
}
