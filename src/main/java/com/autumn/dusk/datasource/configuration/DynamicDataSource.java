package com.autumn.dusk.datasource.configuration;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	private final int dataSourceNumber;  
    private AtomicInteger count = new AtomicInteger(0);  
  
    public DynamicDataSource(int dataSourceNumber) {  
        this.dataSourceNumber = dataSourceNumber;  
    }  
	@Override
	protected Object determineCurrentLookupKey() {
		 String typeKey = DataSourceContextHolder.getDataSourceType().getType(); 
		 if(DataSourceType.MASTER.getType().equals(typeKey))
	          return DataSourceType.MASTER.getType();  
	     // 读 简单负载均衡  
	     int number = count.getAndAdd(1);  
	     int lookupKey = number % dataSourceNumber;  
	     return new Integer(lookupKey); 
	}
}
