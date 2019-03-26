package com.sse.quartz.spring.boot.autoconfigure.properties.core;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigRAMJobStore.html
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigJobStoreTX.html
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigJobStoreCMT.html
 */
public class JobStoreProperties {

    /**
     * 60000 <br>
     *
     * The the number of milliseconds the scheduler will ‘tolerate’ a trigger to pass its next-fire-time by, before being considered “misfired”.
     * The default value (if you don’t make an entry of this property in your configuration) is 60000 (60 seconds).
     */
    private Integer misfireThreshold;

    /**
     *
     * Driver delegates understand the particular ‘dialects’ of varies database systems. Possible choices include:
     * org.quartz.impl.jdbcjobstore.StdJDBCDelegate (for fully JDBC-compliant drivers)
     * org.quartz.impl.jdbcjobstore.MSSQLDelegate (for Microsoft SQL Server, and Sybase)
     * org.quartz.impl.jdbcjobstore.PostgreSQLDelegate
     * org.quartz.impl.jdbcjobstore.WebLogicDelegate (for WebLogic drivers)
     * org.quartz.impl.jdbcjobstore.oracle.OracleDelegate
     * org.quartz.impl.jdbcjobstore.oracle.WebLogicOracleDelegate (for Oracle drivers used within Weblogic)
     * org.quartz.impl.jdbcjobstore.oracle.weblogic.WebLogicOracleDelegate (for Oracle drivers used within Weblogic)
     * org.quartz.impl.jdbcjobstore.CloudscapeDelegate
     * org.quartz.impl.jdbcjobstore.DB2v6Delegate
     * org.quartz.impl.jdbcjobstore.DB2v7Delegate
     * org.quartz.impl.jdbcjobstore.DB2v8Delegate
     * org.quartz.impl.jdbcjobstore.HSQLDBDelegate
     * org.quartz.impl.jdbcjobstore.PointbaseDelegate
     * org.quartz.impl.jdbcjobstore.SybaseDelegate
     * Note that many databases are known to work with the StdJDBCDelegate, while others are known to work with delegates for other databases, for example Derby works well with the Cloudscape delegate (no surprise there).
     */
    private String driverDelegateClass;

    /**
     * The value of this property must be the name of one the DataSources defined in the configuration properties file.
     * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigDataSources.html
     *
     * The value of this property must be the name of one the DataSources defined in the configuration properties file.
     * For JobStoreCMT, it is required that this DataSource contains connections that are capable of participating in JTA (e.g. container-managed) transactions.
     * This typically means that the DataSource will be configured and maintained within and by the application server, and Quartz will obtain a handle to it via JNDI.
     * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigJobStoreCMT.html
     */
    private String dataSource;

    /**
     * QRTZ_ <br>
     *
     * JDBCJobStore’s “table prefix” property is a string equal to the prefix given to Quartz’s tables that were created in your database.
     * You can have multiple sets of Quartz’s tables within the same database if they use different table prefixes.
     */
    private String tablePrefix;

    /**
     * false <br>
     *
     * The “use properties” flag instructs JDBCJobStore that all values in JobDataMaps will be Strings, and therefore can be stored as name-value pairs, rather than storing more complex objects in their serialized form in the BLOB column.
     * This is can be handy, as you avoid the class versioning issues that can arise from serializing your non-String classes into a BLOB.
     */
    private Boolean useProperties;

    /**
     * false <br>
     *
     * Set to “true” in order to turn on clustering features. This property must be set to “true” if you are having multiple instances of Quartz use the same set of database tables… otherwise you will experience havoc.
     * See the configuration docs for clustering for more information.
     */
    private Boolean isClustered;

    /**
     * Set the frequency (in milliseconds) at which this instance “checks-in”* with the other instances of the cluster.
     * Affects the quickness of detecting failed instances.
     */
    private Long clusterCheckinInterval;

    /**
     * The maximum number of misfired triggers the jobstore will handle in a given pass.
     * Handling many (more than a couple dozen) at once can cause the database tables to be locked long enough that the performance of firing other (not yet misfired) triggers may be hampered.
     */
    private Integer maxMisfiresToHandleAtATime;

    /**
     * Setting this parameter to “true” tells Quartz not to call setAutoCommit(false) on connections obtained from the DataSource(s).
     * This can be helpful in a few situations, such as if you have a driver that complains if it is called when it is already off.
     * This property defaults to false, because most drivers require that setAutoCommit(false) is called.
     */
    private Boolean dontSetAutoCommitFalse;

    /**
     * Must be a SQL string that selects a row in the “LOCKS” table and places a lock on the row.
     * If not set, the default is “SELECT * FROM {0}LOCKS WHERE SCHED_NAME = {1} AND LOCK_NAME = ? FOR UPDATE”, which works for most databases. The “{0}” is replaced during run-time with the TABLE_PREFIX that you configured above.
     * The “{1}” is replaced with the scheduler’s name.
     */
    private String selectWithLockSQL;

    /**
     * A value of “true” tells Quartz (when using JobStoreTX or CMT) to call setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE) on JDBC connections.
     * This can be helpful to prevent lock timeouts with some databases under high load, and “long-lasting” transactions.
     */
    private Boolean txIsolationLevelSerializable;

    /**
     * Whether or not the acquisition of next triggers to fire should occur within an explicit database lock.
     * This was once necessary (in previous versions of Quartz) to avoid dead-locks with particular databases, but is no longer considered necessary, hence the default value is “false”.
     *
     * If “org.quartz.scheduler.batchTriggerAcquisitionMaxCount” is set to > 1, and JDBC JobStore is used, then this property must be set to “true” to avoid data corruption (as of Quartz 2.1.1 “true” is now the default if batchTriggerAcquisitionMaxCount is set > 1).
     */
    private Boolean acquireTriggersWithinLock;

    /**
     * A pipe-delimited list of properties (and their values) that can be passed to the DriverDelegate during initialization time.
     *
     * The format of the string is as such:
     *
     * "settingName=settingValue|otherSettingName=otherSettingValue|..."
     * The StdJDBCDelegate and all of its descendants (all delegates that ship with Quartz) support a property called ‘triggerPersistenceDelegateClasses’ which can be set to a comma-separated list of classes that implement the TriggerPersistenceDelegate interface for storing custom trigger types.
     * See the Java classes SimplePropertiesTriggerPersistenceDelegateSupport and SimplePropertiesTriggerPersistenceDelegateSupport for examples of writing a persistence delegate for a custom trigger.
     */
    private String driverDelegateInitString;

    /**
     * JobStoreCMT requires a (second) datasource that contains connections that will not be part of container-managed transactions.
     * The value of this property must be the name of one the DataSources defined in the configuration properties file.
     * This datasource must contain non-CMT connections, or in other words, connections for which it is legal for Quartz to directly call commit() and rollback() on.
     */
    private String nonManagedTXDataSource;

    /**
     * false <br>
     * The same as the property org.quartz.jobStore.dontSetAutoCommitFalse, except that it applies to the nonManagedTXDataSource.
     */
    private Boolean dontSetNonManagedTXConnectionAutoCommitFalse;

    /**
     * false <br>
     * When set to “true”, this property tells Quartz to call setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED) on the non-managed JDBC connections.
     * This can be helpful to prevent lock timeouts with some databases (such as DB2) under high load, and “long-lasting” transactions.
     */
    private Boolean txIsolationLevelReadCommitted;

    /**
     * The host and port identifying the location of the Terracotta server to connect to, such as “localhost:9510”.
     */
    private String tcConfigUrl;

    /**
     * host for redis...
     */
    private String host;

    /**
     * port for redis...
     */
    private Integer port;

    /**
     * password for redis...
     */
    private String password;

    /**
     * database for redis...
     */
    private Integer database;

    /**
     * using ssl or not for redis
     */
    private Boolean ssl;

    /**
     * redis lock's default expire time of milliseconds
     */
    private Long expireInMills;

    public Integer getMisfireThreshold() {
        return misfireThreshold;
    }

    public void setMisfireThreshold(Integer misfireThreshold) {
        this.misfireThreshold = misfireThreshold;
    }

    public String getDriverDelegateClass() {
        return driverDelegateClass;
    }

    public void setDriverDelegateClass(String driverDelegateClass) {
        this.driverDelegateClass = driverDelegateClass;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Boolean getUseProperties() {
        return useProperties;
    }

    public void setUseProperties(Boolean useProperties) {
        this.useProperties = useProperties;
    }

    public Boolean getClustered() {
        return isClustered;
    }

    public void setClustered(Boolean clustered) {
        isClustered = clustered;
    }

    public Long getClusterCheckinInterval() {
        return clusterCheckinInterval;
    }

    public void setClusterCheckinInterval(Long clusterCheckinInterval) {
        this.clusterCheckinInterval = clusterCheckinInterval;
    }

    public Integer getMaxMisfiresToHandleAtATime() {
        return maxMisfiresToHandleAtATime;
    }

    public void setMaxMisfiresToHandleAtATime(Integer maxMisfiresToHandleAtATime) {
        this.maxMisfiresToHandleAtATime = maxMisfiresToHandleAtATime;
    }

    public Boolean getDontSetAutoCommitFalse() {
        return dontSetAutoCommitFalse;
    }

    public void setDontSetAutoCommitFalse(Boolean dontSetAutoCommitFalse) {
        this.dontSetAutoCommitFalse = dontSetAutoCommitFalse;
    }

    public String getSelectWithLockSQL() {
        return selectWithLockSQL;
    }

    public void setSelectWithLockSQL(String selectWithLockSQL) {
        this.selectWithLockSQL = selectWithLockSQL;
    }

    public Boolean getTxIsolationLevelSerializable() {
        return txIsolationLevelSerializable;
    }

    public void setTxIsolationLevelSerializable(Boolean txIsolationLevelSerializable) {
        this.txIsolationLevelSerializable = txIsolationLevelSerializable;
    }

    public Boolean getAcquireTriggersWithinLock() {
        return acquireTriggersWithinLock;
    }

    public void setAcquireTriggersWithinLock(Boolean acquireTriggersWithinLock) {
        this.acquireTriggersWithinLock = acquireTriggersWithinLock;
    }

    public String getDriverDelegateInitString() {
        return driverDelegateInitString;
    }

    public void setDriverDelegateInitString(String driverDelegateInitString) {
        this.driverDelegateInitString = driverDelegateInitString;
    }

    public String getNonManagedTXDataSource() {
        return nonManagedTXDataSource;
    }

    public void setNonManagedTXDataSource(String nonManagedTXDataSource) {
        this.nonManagedTXDataSource = nonManagedTXDataSource;
    }

    public Boolean getDontSetNonManagedTXConnectionAutoCommitFalse() {
        return dontSetNonManagedTXConnectionAutoCommitFalse;
    }

    public void setDontSetNonManagedTXConnectionAutoCommitFalse(Boolean dontSetNonManagedTXConnectionAutoCommitFalse) {
        this.dontSetNonManagedTXConnectionAutoCommitFalse = dontSetNonManagedTXConnectionAutoCommitFalse;
    }

    public Boolean getTxIsolationLevelReadCommitted() {
        return txIsolationLevelReadCommitted;
    }

    public void setTxIsolationLevelReadCommitted(Boolean txIsolationLevelReadCommitted) {
        this.txIsolationLevelReadCommitted = txIsolationLevelReadCommitted;
    }

    public String getTcConfigUrl() {
        return tcConfigUrl;
    }

    public void setTcConfigUrl(String tcConfigUrl) {
        this.tcConfigUrl = tcConfigUrl;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public Long getExpireInMills() {
        return expireInMills;
    }

    public void setExpireInMills(Long expireInMills) {
        this.expireInMills = expireInMills;
    }
}
