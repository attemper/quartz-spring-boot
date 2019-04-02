package com.sse.quartz.spring.boot.autoconfigure.db;

import javax.sql.DataSource;

public class DataSourceHolder {

    private static final ThreadLocal<DataSource> contextHolder = new ThreadLocal();

    public static void set(DataSource Tenant) {
        contextHolder.set(Tenant);
    }

    public static DataSource get() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
