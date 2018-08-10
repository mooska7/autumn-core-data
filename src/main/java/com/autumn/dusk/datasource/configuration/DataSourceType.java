package com.autumn.dusk.datasource.configuration;

/**
 * 数据源主从库枚举
 * @author mooska
 *
 */
public enum DataSourceType {
	/**
	 * 主库
	 */
    MASTER("master","主库"),
    /**
	 * 从库
	 */
    CLUSTER("cluster","写库");
    private String type;
    private String name;
    
    DataSourceType(String type,String name) {
    	this.type = type;
    	this.name= name;
    }
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
