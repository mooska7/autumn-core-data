package com.autumn.dusk.datasource.configuration;

/**
 * 当前数据源保持
 * @author mooska
 *
 */
public class DataSourceContextHolder {
private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>();
    
    public static void setDataSourceType(DataSourceType type){
        contextHolder.set(type);
    }
    
    public static DataSourceType getDataSourceType(){
        return contextHolder.get();
    }

}
