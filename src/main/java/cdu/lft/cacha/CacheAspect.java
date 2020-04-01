package cdu.lft.cacha;

import cdu.lft.bean.Dept;
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

    //声明一个缓存容器
    private Map<String,Object>  CACHE_CONTAINER=new HashMap<>();

    //声明切面表达式
    private static final String POINTCUT_DEPT_UPDATE="execution(* cdu.lft.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GETONE="execution(* cdu.lft.service.impl.DeptServiceImpl.getOne(..))";
    private static final String POINTCUT_DEPT_REMOVE="execution(* cdu.lft.service.impl.DeptServiceImpl.removeById(..))";

    private static final String CACHE_DEPT_PROFIX="dept:";

    /*
    * 查询切入
    * */
    @Around(value = POINTCUT_DEPT_GETONE)
    public Dept cacheDeptGetOne(ProceedingJoinPoint joinPoint) throws Throwable {

        //取出第一个参数
        Object object=joinPoint.getArgs()[0];

        Dept result1=(Dept)CACHE_CONTAINER.get(CACHE_DEPT_PROFIX+object);
        if (result1!=null){
            return result1;
        }else {
            Dept result2=(Dept)joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+object,result2);
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
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+object.getId(),object);
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
}
