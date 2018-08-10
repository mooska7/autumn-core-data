package com.autumn.dusk.datasource.configuration;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 数据源切换配置
 * @author mooska
 *
 */
@Aspect
@Component
public class DataSourceAspect {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
     * 使用空方法定义切点表达式
     */
    @Pointcut("execution(* com.autumn.dusk.*.dao.*.get*(..))")
    public void masterExpression() {
    }
    
    /**
     * 使用空方法定义切点表达式
     */
    @Pointcut("execution(* com.autumn.dusk.*.dao.*.update*(..)) "
    		+ "|| execution(* com.autumn.dusk.dao.*.*.save*(..)) "
    		+ "||execution(* com.autumn.dusk.dao.*.*.del*(..))")
    public void clusterExpression() {
    }
    
    /**
     * 使用定义切点表达式的方法进行切点表达式的引入,根据dao所在的包名进行切换DB
     */
    @Before("masterExpression()")
    public void setMasterDataSourceKey(JoinPoint point) {
    	logger.info("-----------切换到"+DataSourceType.MASTER.getName()+"-----------");
        DataSourceContextHolder.setDataSourceType(DataSourceType.MASTER);
            
       
    }

    /**
     * 使用定义切点表达式的方法进行切点表达式的引入,根据dao所在的包名进行切换DB
     */
    @Before("clusterExpression()")
    public void setClusterDataSourceKey(JoinPoint point) {
    	logger.info("-----------切换到"+DataSourceType.CLUSTER.getName()+"-----------");
        DataSourceContextHolder.setDataSourceType(DataSourceType.CLUSTER);
    }

}
