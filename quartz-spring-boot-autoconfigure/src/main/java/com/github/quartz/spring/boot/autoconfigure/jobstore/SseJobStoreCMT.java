package com.github.quartz.spring.boot.autoconfigure.jobstore;

import com.github.quartz.spring.boot.autoconfigure.db.CustomConnectionProvider;
import com.github.quartz.spring.boot.autoconfigure.db.DataSourceHolder;
import org.quartz.SchedulerConfigException;
import org.quartz.impl.jdbcjobstore.JobStoreCMT;
import org.quartz.impl.jdbcjobstore.SimpleSemaphore;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.utils.DBConnectionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.sql.Connection;

public class SseJobStoreCMT extends JobStoreCMT {
    private static final String TX_DATA_SOURCE_PREFIX = "sseTxDataSource";
    private static final String NON_TX_DATA_SOURCE_PREFIX = "sseNonTxDataSource";
    @Nullable
    private DataSource dataSource;

    public void initialize(ClassLoadHelper loadHelper, SchedulerSignaler signaler) throws SchedulerConfigException {
        this.dataSource = DataSourceHolder.get();
        if (this.dataSource == null) {
            throw new SchedulerConfigException("No local DataSource found for configuration - 'dataSource' property must be set on SchedulerFactoryBean");
        } else {
            this.setDataSource(TX_DATA_SOURCE_PREFIX);
            this.setDontSetAutoCommitFalse(true);
            DBConnectionManager.getInstance().addConnectionProvider(TX_DATA_SOURCE_PREFIX, new CustomConnectionProvider(this.dataSource));
            this.setNonManagedTXDataSource(NON_TX_DATA_SOURCE_PREFIX);
            DBConnectionManager.getInstance().addConnectionProvider(NON_TX_DATA_SOURCE_PREFIX, new CustomConnectionProvider(this.dataSource));
            try {
                String productName = (String)JdbcUtils.extractDatabaseMetaData(this.dataSource, "getDatabaseProductName");
                productName = JdbcUtils.commonDatabaseName(productName);
                if (productName.toLowerCase().contains("hsql")) {
                    this.setUseDBLocks(false);
                    this.setLockHandler(new SimpleSemaphore());
                }
            } catch (MetaDataAccessException var6) {
                this.logWarnIfNonZero(1, "Could not detect database type. Assuming locks can be taken.");
            }

            super.initialize(loadHelper, signaler);
        }
    }

    protected void closeConnection(Connection con) {
        DataSourceUtils.releaseConnection(con, this.dataSource);
    }
}
