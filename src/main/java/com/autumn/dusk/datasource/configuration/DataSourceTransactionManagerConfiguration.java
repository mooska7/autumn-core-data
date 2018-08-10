package com.autumn.dusk.datasource.configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
/**
 * 重新定义事务
 *
 */
@Configuration  
@EnableTransactionManagement  
public class DataSourceTransactionManagerConfiguration{
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** 
     * 自定义事务 
     * MyBatis自动参与到spring事务管理中，无需额外配置，只要org.mybatis.spring.SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源一致即可，否则事务管理会不起作用。 
     * @return 
     */  
    @Resource(name = "roundRobinDataSouceProxy")
    private DataSource dataSource;  
    
    @Bean(name = "transactionManager")  
    public DataSourceTransactionManager transactionManager() {
    	logger.info("-------------------- transactionManager init ---------------------");
    	return new MultiDataSourceTransactionManager(dataSource);
    }  
    
    @Bean(name = "transactionTemplate")  
    public TransactionTemplate transactionTemplate() {
    	logger.info("-------------------- transactionTemplate init ---------------------");
    	return new TransactionTemplate(transactionManager());
    }  
}
