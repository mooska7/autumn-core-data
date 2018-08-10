package com.autumn.dusk.datasource.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {
	private final Logger logger =LoggerFactory.getLogger(this.getClass());
	@Value("${spring.datasource.type}")
	private Class<? extends DataSource> dataSourceType;
	
	@Bean(name = "masterDataSource",destroyMethod = "close", initMethod="init")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource masterDataSource() {
		logger.info("-------------------- masterDataSource init ---------------------");
		return DataSourceBuilder.create().type(dataSourceType).build();
	}
	
	@Bean(name = "clusterDataSource",destroyMethod = "close", initMethod="init")
	@ConfigurationProperties(prefix = "spring.datasource.cluster")
	public DataSource clusterDataSource() {
		logger.info("-------------------- clusterDataSource init ---------------------");
		return DataSourceBuilder.create().type(dataSourceType).build();
	}
	
	@Bean("clusterDataSources")
	public List<DataSource> readDataSources() {
		List<DataSource> dataSources = new ArrayList<>();
		dataSources.add(clusterDataSource());
		return dataSources;
	}
}
