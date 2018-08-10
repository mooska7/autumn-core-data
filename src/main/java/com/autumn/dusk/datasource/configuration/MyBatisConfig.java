package com.autumn.dusk.datasource.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@Import({ DataSourceConfiguration.class })
@MapperScan(basePackages = "com.autumn.dusk.*.dao")
public class MyBatisConfig {

	@Value("${datasource.clusterSize}")
	private String clusterDataSourceSize;
	@Resource(name = "masterDataSource")
	private DataSource masterDataSource;
	@Resource(name = "clusterDataSources")
	private List<DataSource> clusterDataSources;

	/**
	 * 根据数据源创建SqlSessionFactory
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());// 指定数据源(这个必须有，否则报错)
		sqlSessionFactoryBean.setTypeAliasesPackage("com.autumn.dusk.*.dao");// 指定基包
		sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		// fb.setMapperLocations(
		// // new
		// //
		// PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")
		// new
		// PathMatchingResourcePatternResolver().getResources("classpath:com/yungui/data/dao/*/*.xml"));//
		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
	 * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
	 */
	@Bean(name = "roundRobinDataSouceProxy")
	public DataSource roundRobinDataSouceProxy() {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.CLUSTER.getType(), masterDataSource);
		int size = Integer.parseInt(clusterDataSourceSize);
		for (int i = 0; i < size; i++) {
			targetDataSources.put(i, clusterDataSources.get(i));
		}
		DynamicDataSource dynamicDataSource = new DynamicDataSource(size);
		dynamicDataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
		dynamicDataSource.setDefaultTargetDataSource(masterDataSource);// 默认的datasource设置为myTestDbDataSource
		return dynamicDataSource;
	}


}