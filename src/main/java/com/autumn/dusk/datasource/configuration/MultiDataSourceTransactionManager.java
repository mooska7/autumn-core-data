package com.autumn.dusk.datasource.configuration;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class MultiDataSourceTransactionManager extends DataSourceTransactionManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8478667649867892934L;

	public MultiDataSourceTransactionManager(DataSource dataSource) {
		super(dataSource);
	}

	protected Object doGetTransaction() {
		DataSourceContextHolder.setDataSourceType(DataSourceType.MASTER);
		return super.doGetTransaction();
	}

}
