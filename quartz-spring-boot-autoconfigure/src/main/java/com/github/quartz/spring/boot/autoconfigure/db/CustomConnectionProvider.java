package com.github.quartz.spring.boot.autoconfigure.db;

import org.quartz.utils.ConnectionProvider;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CustomConnectionProvider implements ConnectionProvider {

    private final DataSource dataSource;

    public CustomConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(this.dataSource);
    }

    @Override
    public void shutdown() throws SQLException {

    }

    @Override
    public void initialize() throws SQLException {

    }
}