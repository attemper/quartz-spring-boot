package com.sse.quartz.spring.boot.autoconfigure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("org.quartz")
public class QuartzProperties {

    private final SchedulerProperties scheduler = new SchedulerProperties();

    private final ContextProperties context = new ContextProperties();

    private final ThreadPoolProperties threadPool = new ThreadPoolProperties();

    /**
     * key = value like <br>
     * NAME.class = com.foo.MyListenerClass
     * NAME.propName = propValue
     * NAME.prop2Name = prop2Value
     */
    private Map<String, String> triggerListener = new HashMap<>();

    /**
     * key = value like <br>
     * NAME.class = com.foo.MyListenerClass
     * NAME.propName = propValue
     * NAME.prop2Name = prop2Value
     */
    private final Map<String, String> jobListener = new HashMap<>();

    private final PluginProperties plugin = new PluginProperties();

    private final JobStoreProperties jobStore = new JobStoreProperties();

    private final DataStoreProperties dataSource = new DataStoreProperties();

    public SchedulerProperties getScheduler() {
        return scheduler;
    }

    public ThreadPoolProperties getThreadPool() {
        return threadPool;
    }

    public JobStoreProperties getJobStore() {
        return jobStore;
    }

    public DataStoreProperties getDataSource() {
        return dataSource;
    }

    /**
     * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigMain.html
     */
    private static class SchedulerProperties {

        /**
         * QuartzScheduler <br>
         *
         * Can be any string, and the value has no meaning to the scheduler itself -
         * but rather serves as a mechanism for client code to distinguish schedulers when multiple instances are used within the same program.
         * If you are using the clustering features, you must use the same name for every instance in the cluster that is ‘logically’ the same Scheduler.
         */
        private String instanceName;

        /**
         * NON_CLUSTERED <br>
         *
         * Can be any string, but must be unique for all schedulers working as if they are the same ‘logical’ Scheduler within a cluster.
         * You may use the value “AUTO” as the instanceId if you wish the Id to be generated for you. Or the value “SYS_PROP” if you want the value to come from the system property “org.quartz.scheduler.instanceId”.
         */
        private String instanceId;

        /**
         * org.quartz.simpl.SimpleInstanceIdGenerator <br>
         *
         * Only used if org.quartz.scheduler.instanceId is set to “AUTO”.
         * Defaults to “org.quartz.simpl.SimpleInstanceIdGenerator”, which generates an instance id based upon host name and time stamp.
         * Other IntanceIdGenerator implementations include SystemPropertyInstanceIdGenerator (which gets the instance id from the system property “org.quartz.scheduler.instanceId”, and HostnameInstanceIdGenerator which uses the local host name (InetAddress.getLocalHost().getHostName()).
         * You can also implement the InstanceIdGenerator interface your self.
         */
        @Value("${instanceIdGenerator.class}")
        private String instanceIdGeneratorClass;

        /**
         * 	instanceName + '_QuartzSchedulerThread' <br>
         *
         * Can be any String that is a valid name for a java thread.
         * If this property is not specified, the thread will receive the scheduler’s name (“org.quartz.scheduler.instanceName”) plus an the appended string ‘_QuartzSchedulerThread’.
         */
        private String threadName;

        /**
         * false <br>
         *
         * A boolean value (‘true’ or ‘false’) that specifies whether the main thread of the scheduler should be a daemon thread or not.
         * See also the org.quartz.scheduler.makeSchedulerThreadDaemon property for tuning the SimpleThreadPool if that is the thread pool implementation you are using (which is most likely the case).
         */
        private Boolean makeSchedulerThreadDaemon;

        /**
         * false <br>
         *
         * A boolean value (‘true’ or ‘false’) that specifies whether the threads spawned by Quartz will inherit the context ClassLoader of the initializing thread (thread that initializes the Quartz instance).
         * This will affect Quartz main scheduling thread, JDBCJobStore’s misfire handling thread (if JDBCJobStore is used), cluster recovery thread (if clustering is used), and threads in SimpleThreadPool (if SimpleThreadPool is used).
         * Setting this value to ‘true’ may help with class loading, JNDI look-ups, and other issues related to using Quartz within an application server.
         */
        private Boolean threadsInheritContextClassLoaderOfInitializer;

        /**
         * 30000 <br>
         *
         * Is the amount of time in milliseconds that the scheduler will wait before re-queries for available triggers when the scheduler is otherwise idle.
         * Normally you should not have to ‘tune’ this parameter, unless you’re using XA transactions, and are having problems with delayed firings of triggers that should fire immediately. Values less than 5000 ms are not recommended as it will cause excessive database querying.
         * Values less than 1000 are not legal.
         */
        private Long idleWaitTime;

        /**
         * 15000 <br>
         *
         * Is the amount of time in milliseconds that the scheduler will wait between re-tries when it has detected a loss of connectivity within the JobStore (e.g. to the database).
         * This parameter is obviously not very meaningful when using RamJobStore.
         */
        private Long dbFailureRetryInterval;

        /**
         * org.quartz.simpl.CascadingClassLoadHelper <br>
         *
         * Defaults to the most robust approach, which is to use the “org.quartz.simpl.CascadingClassLoadHelper” class - which in turn uses every other ClassLoadHelper class until one works.
         * You should probably not find the need to specify any other class for this property, though strange things seem to happen within application servers.
         * All of the current possible ClassLoadHelper implementation can be found in the org.quartz.simpl package.
         */
        @Value("${classLoadHelper.class}")
        private String classLoadHelperClass;

        /**
         * org.quartz.simpl.PropertySettingJobFactory <br>
         *
         * The class name of the JobFactory to use. A JobFatcory is responsible for producing instances of JobClasses.
         * The default is ‘org.quartz.simpl.PropertySettingJobFactory’, which simply calls newInstance() on the class to produce a new instance each time execution is about to occur.
         * PropertySettingJobFactory also reflectively sets the job’s bean properties using the contents of the SchedulerContext and Job and Trigger JobDataMaps.
         */
        @Value("${jobFactory.class}")
        private String jobFactoryClass;

        /**
         * java:comp/UserTransaction <br>
         *
         * Should be set to the JNDI URL at which Quartz can locate the Application Server’s UserTransaction manager.
         * The default value (if not specified) is “java:comp/UserTransaction” - which works for almost all Application Servers. Websphere users may need to set this property to “jta/usertransaction”.
         * This is only used if Quartz is configured to use JobStoreCMT, and org.quartz.scheduler.wrapJobExecutionInUserTransaction is set to true.
         */
        private String userTransactionURL;

        /**
         * false <br>
         *
         * Should be set to “true” if you want Quartz to start a UserTransaction before calling execute on your job.
         * The Tx will commit after the job’s execute method completes, and after the JobDataMap is updated (if it is a StatefulJob). The default value is “false”.
         * You may also be interested in using the @ExecuteInJTATransaction annotation on your job class, which lets you control for an individual job whether Quartz should start a JTA transaction - whereas this property causes it to occur for all jobs.
         */
        private Boolean wrapJobExecutionInUserTransaction;

        /**
         * false <br>
         *
         * Whether or not to skip running a quick web request to determine if there is an updated version of Quartz available for download.
         * If the check runs, and an update is found, it will be reported as available in Quartz’s logs.
         * You can also disable the update check with the system property “org.terracotta.quartz.skipUpdateCheck=true” (which you can set in your system environment or as a -D on the java command line).
         * It is recommended that you disable the update check for production deployments.
         */
        private Boolean skipUpdateCheck;

        /**
         * 1 <br>
         *
         * The maximum number of triggers that a scheduler node is allowed to acquire (for firing) at once. Default value is 1.
         * The larger the number, the more efficient firing is (in situations where there are very many triggers needing to be fired all at once) - but at the cost of possible imbalanced load between cluster nodes.
         * If the value of this property is set to > 1, and JDBC JobStore is used, then the property “org.quartz.jobStore.acquireTriggersWithinLock” must be set to “true” to avoid data corruption.
         */
        private Integer batchTriggerAcquisitionMaxCount;

        /**
         * 0 <br>
         *
         * The amount of time in milliseconds that a trigger is allowed to be acquired and fired ahead of its scheduled fire time.
         * Defaults to 0. The larger the number, the more likely batch acquisition of triggers to fire will be able to select and fire more than 1 trigger at a time - at the cost of trigger schedule not being honored precisely (triggers may fire this amount early).
         * This may be useful (for performance’s sake) in situations where the scheduler has very large numbers of triggers that need to be fired at or near the same time.
         */
        private Long batchTriggerAcquisitionFireAheadTimeWindow;

        private RmiProperties rmi = new RmiProperties();

        public String getInstanceName() {
            return instanceName;
        }

        public void setInstanceName(String instanceName) {
            this.instanceName = instanceName;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
        }

        public String getInstanceIdGeneratorClass() {
            return instanceIdGeneratorClass;
        }

        public void setInstanceIdGeneratorClass(String instanceIdGeneratorClass) {
            this.instanceIdGeneratorClass = instanceIdGeneratorClass;
        }

        public String getThreadName() {
            return threadName;
        }

        public void setThreadName(String threadName) {
            this.threadName = threadName;
        }

        public Boolean getMakeSchedulerThreadDaemon() {
            return makeSchedulerThreadDaemon;
        }

        public void setMakeSchedulerThreadDaemon(Boolean makeSchedulerThreadDaemon) {
            this.makeSchedulerThreadDaemon = makeSchedulerThreadDaemon;
        }

        public Boolean getThreadsInheritContextClassLoaderOfInitializer() {
            return threadsInheritContextClassLoaderOfInitializer;
        }

        public void setThreadsInheritContextClassLoaderOfInitializer(Boolean threadsInheritContextClassLoaderOfInitializer) {
            this.threadsInheritContextClassLoaderOfInitializer = threadsInheritContextClassLoaderOfInitializer;
        }

        public Long getIdleWaitTime() {
            return idleWaitTime;
        }

        public void setIdleWaitTime(Long idleWaitTime) {
            this.idleWaitTime = idleWaitTime;
        }

        public Long getDbFailureRetryInterval() {
            return dbFailureRetryInterval;
        }

        public void setDbFailureRetryInterval(Long dbFailureRetryInterval) {
            this.dbFailureRetryInterval = dbFailureRetryInterval;
        }

        public String getClassLoadHelperClass() {
            return classLoadHelperClass;
        }

        public void setClassLoadHelperClass(String classLoadHelperClass) {
            this.classLoadHelperClass = classLoadHelperClass;
        }

        public String getJobFactoryClass() {
            return jobFactoryClass;
        }

        public void setJobFactoryClass(String jobFactoryClass) {
            this.jobFactoryClass = jobFactoryClass;
        }

        public String getUserTransactionURL() {
            return userTransactionURL;
        }

        public void setUserTransactionURL(String userTransactionURL) {
            this.userTransactionURL = userTransactionURL;
        }

        public Boolean getWrapJobExecutionInUserTransaction() {
            return wrapJobExecutionInUserTransaction;
        }

        public void setWrapJobExecutionInUserTransaction(Boolean wrapJobExecutionInUserTransaction) {
            this.wrapJobExecutionInUserTransaction = wrapJobExecutionInUserTransaction;
        }

        public Boolean getSkipUpdateCheck() {
            return skipUpdateCheck;
        }

        public void setSkipUpdateCheck(Boolean skipUpdateCheck) {
            this.skipUpdateCheck = skipUpdateCheck;
        }

        public Integer getBatchTriggerAcquisitionMaxCount() {
            return batchTriggerAcquisitionMaxCount;
        }

        public void setBatchTriggerAcquisitionMaxCount(Integer batchTriggerAcquisitionMaxCount) {
            this.batchTriggerAcquisitionMaxCount = batchTriggerAcquisitionMaxCount;
        }

        public Long getBatchTriggerAcquisitionFireAheadTimeWindow() {
            return batchTriggerAcquisitionFireAheadTimeWindow;
        }

        public void setBatchTriggerAcquisitionFireAheadTimeWindow(Long batchTriggerAcquisitionFireAheadTimeWindow) {
            this.batchTriggerAcquisitionFireAheadTimeWindow = batchTriggerAcquisitionFireAheadTimeWindow;
        }

        public RmiProperties getRmi() {
            return rmi;
        }

        public void setRmi(RmiProperties rmi) {
            this.rmi = rmi;
        }

        /**
         * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigRMI.html
         */
        private static class RmiProperties {
            private Boolean export;

            private String registryHost;

            private Integer registryPort;

            private String createRegistry;

            private Integer serverPort;

            private Boolean proxy;

            public Boolean getExport() {
                return export;
            }

            public void setExport(Boolean export) {
                this.export = export;
            }

            public String getRegistryHost() {
                return registryHost;
            }

            public void setRegistryHost(String registryHost) {
                this.registryHost = registryHost;
            }

            public Integer getRegistryPort() {
                return registryPort;
            }

            public void setRegistryPort(Integer registryPort) {
                this.registryPort = registryPort;
            }

            public String getCreateRegistry() {
                return createRegistry;
            }

            public void setCreateRegistry(String createRegistry) {
                this.createRegistry = createRegistry;
            }

            public Integer getServerPort() {
                return serverPort;
            }

            public void setServerPort(Integer serverPort) {
                this.serverPort = serverPort;
            }

            public Boolean getProxy() {
                return proxy;
            }

            public void setProxy(Boolean proxy) {
                this.proxy = proxy;
            }
        }
    }

    /**
     * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigMain.html
     */
    private static class ContextProperties{

        /**
         * Represent a name-value pair that will be placed into the “scheduler context” as strings.
         * (see Scheduler.getContext()). So for example, the setting “org.quartz.context.key.MyKey = MyValue” would perform the equivalent of scheduler.getContext().put(“MyKey”, “MyValue”).
         * The Transaction-Related properties should be left out of the config file unless you are using JTA transactions.
         */
        private Map<String, String> key = new HashMap<>();

        public Map<String, String> getKey() {
            return key;
        }

        public void setKey(Map<String, String> key) {
            this.key = key;
        }
    }

    private static class ThreadPoolProperties {

        /**
         * null <br>
         *
         * Is the name of the ThreadPool implementation you wish to use.
         * The threadpool that ships with Quartz is “org.quartz.simpl.SimpleThreadPool”, and should meet the needs of nearly every user.
         * It has very simple behavior and is very well tested.
         * It provides a fixed-size pool of threads that ‘live’ the lifetime of the Scheduler.
         */
        @Value("${threadPool.class}")
        private String threadPoolClass;

        /**
         * -1 <br>
         *
         * Can be any positive integer, although you should realize that only numbers between 1 and 100 are very practical.
         * This is the number of threads that are available for concurrent execution of jobs.
         * If you only have a few jobs that fire a few times a day, then 1 thread is plenty!
         * If you have tens of thousands of jobs, with many firing every minute, then you probably want a thread count more like 50 or 100 (this highly depends on the nature of the work that your jobs perform, and your systems resources!).
         */
        private Integer threadCount;

        /**
         * Thread.NORM_PRIORITY (5) <br>
         *
         * Can be any int between Thread.MIN_PRIORITY (which is 1) and Thread.MAX_PRIORITY (which is 10).
         * The default is Thread.NORM_PRIORITY (5).
         *
         */
        private Integer threadPriority;

        /**
         * false <br>
         *
         * Can be set to “true” to have the threads in the pool created as daemon threads.
         * Default is “false”. See also the org.quartz.scheduler.makeSchedulerThreadDaemon property.
         */
        private Boolean makeThreadsDaemons;

        /**
         * true <br>
         *
         * Can be “true” or “false”, and defaults to true.
         */
        private Boolean threadsInheritGroupOfInitializingThread;

        /**
         * false <br>
         *
         * Can be “true” or “false”, and defaults to false.
         */
        private Boolean threadsInheritContextClassLoaderOfInitializingThread;

        /**
         * [Scheduler Name]_Worker <br>
         *
         * The prefix for thread names in the worker pool - will be postpended with a number.
         */
        private String threadNamePrefix;

        public String getThreadPoolClass() {
            return threadPoolClass;
        }

        public void setThreadPoolClass(String threadPoolClass) {
            this.threadPoolClass = threadPoolClass;
        }

        public Integer getThreadCount() {
            return threadCount;
        }

        public void setThreadCount(Integer threadCount) {
            this.threadCount = threadCount;
        }

        public Integer getThreadPriority() {
            return threadPriority;
        }

        public void setThreadPriority(Integer threadPriority) {
            this.threadPriority = threadPriority;
        }

        public Boolean getMakeThreadsDaemons() {
            return makeThreadsDaemons;
        }

        public void setMakeThreadsDaemons(Boolean makeThreadsDaemons) {
            this.makeThreadsDaemons = makeThreadsDaemons;
        }

        public Boolean getThreadsInheritGroupOfInitializingThread() {
            return threadsInheritGroupOfInitializingThread;
        }

        public void setThreadsInheritGroupOfInitializingThread(Boolean threadsInheritGroupOfInitializingThread) {
            this.threadsInheritGroupOfInitializingThread = threadsInheritGroupOfInitializingThread;
        }

        public Boolean getThreadsInheritContextClassLoaderOfInitializingThread() {
            return threadsInheritContextClassLoaderOfInitializingThread;
        }

        public void setThreadsInheritContextClassLoaderOfInitializingThread(Boolean threadsInheritContextClassLoaderOfInitializingThread) {
            this.threadsInheritContextClassLoaderOfInitializingThread = threadsInheritContextClassLoaderOfInitializingThread;
        }

        public String getThreadNamePrefix() {
            return threadNamePrefix;
        }

        public void setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
        }
    }

    /**
     * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigRAMJobStore.html
     * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigJobStoreTX.html
     *
     */
    private static class JobStoreProperties {

        /**
         * @see org.quartz.simpl.RAMJobStore <br>
         * @see org.quartz.impl.jdbcjobstore.JobStoreTX <br>
         */
        @Value("${class}")
        private String className;

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
         * See the configuration docs for DataSources for more information.
         * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigDataSources.html
         */
        private DataSourceProp datasource = new DataSourceProp();

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
         * The class name to be used to produce an instance of a org.quartz.impl.jdbcjobstore.Semaphore to be used for locking control on the job store data.
         * This is an advanced configuration feature, which should not be used by most users.
         * By default, Quartz will select the most appropriate (pre-bundled) Semaphore implementation to use.
         * “org.quartz.impl.jdbcjobstore.UpdateLockRowSemaphore” QUARTZ-497 may be of interest to MS SQL Server users. See QUARTZ-441.
         */
        @Value("${lockHandler.class}")
        private String lockHandlerClass;

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

        private static class DataSourceProp {

        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

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

        public DataSourceProp getDatasource() {
            return datasource;
        }

        public void setDatasource(DataSourceProp datasource) {
            this.datasource = datasource;
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

        public String getLockHandlerClass() {
            return lockHandlerClass;
        }

        public void setLockHandlerClass(String lockHandlerClass) {
            this.lockHandlerClass = lockHandlerClass;
        }

        public String getDriverDelegateInitString() {
            return driverDelegateInitString;
        }

        public void setDriverDelegateInitString(String driverDelegateInitString) {
            this.driverDelegateInitString = driverDelegateInitString;
        }
    }

    private static class DataStoreProperties {

    }

    private static class PluginProperties {

        /**
         * The logging trigger history plugin catches trigger events (it is also a trigger listener) and logs then with Jakarta Commons-Logging.
         * See the class’s JavaDoc for a list of all the possible parameters.
         */
        private final LoggingTriggerHistoryPluginProperties triggerHistory = new LoggingTriggerHistoryPluginProperties();

        /**
         * Job initialization plugin reads a set of jobs and triggers from an XML file, and adds them to the scheduler during initialization.
         * It can also delete exiting data. See the class’s JavaDoc for more details.
         */
        private final XMLSchedulingDataProcessorPluginProperties jobInitializer = new XMLSchedulingDataProcessorPluginProperties();

        /**
         * The shutdown-hook plugin catches the event of the JVM terminating, and calls shutdown on the scheduler.
         */
        private final ShutdownHookPluginProperties shutdownhook = new ShutdownHookPluginProperties();

        private final LoggingJobHistoryPluginProperties jobHistory = new LoggingJobHistoryPluginProperties();

        private final JobInterruptMonitorPluginProperties jobInterruptMonitor = new JobInterruptMonitorPluginProperties();

        public LoggingTriggerHistoryPluginProperties getTriggerHistory() {
            return triggerHistory;
        }

        public XMLSchedulingDataProcessorPluginProperties getJobInitializer() {
            return jobInitializer;
        }

        public ShutdownHookPluginProperties getShutdownhook() {
            return shutdownhook;
        }

        public LoggingJobHistoryPluginProperties getJobHistory() {
            return jobHistory;
        }

        public JobInterruptMonitorPluginProperties getJobInterruptMonitor() {
            return jobInterruptMonitor;
        }

        /**
         * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigPlugins.html
         */
        private static class LoggingTriggerHistoryPluginProperties {

            /**
             * org.quartz.plugins.history.LoggingTriggerHistoryPlugin <br>
             */
            @Value("${class}")
            private String className;

            /**
             * Trigger \{1\}.\{0\} fired job \{6\}.\{5\} at: \{4, date, HH:mm:ss MM/dd/yyyy} <br>
             */
            private String triggerFiredMessage;

            /**
             * Trigger {1}.{0} misfired job {6}.{5}  at: {4, date, HH:mm:ss MM/dd/yyyy}.  Should have fired at: {3, date, HH:mm:ss MM/dd/yyyy} <br>
             */
            private String triggerMisfiredMessage;

            /**
             * Trigger \{1\}.\{0\} completed firing job \{6\}.\{5\} at \{4, date, HH:mm:ss MM/dd/yyyy\}. <br>
             */
            private String triggerCompleteMessage;

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getTriggerFiredMessage() {
                return triggerFiredMessage;
            }

            public void setTriggerFiredMessage(String triggerFiredMessage) {
                this.triggerFiredMessage = triggerFiredMessage;
            }

            public String getTriggerMisfiredMessage() {
                return triggerMisfiredMessage;
            }

            public void setTriggerMisfiredMessage(String triggerMisfiredMessage) {
                this.triggerMisfiredMessage = triggerMisfiredMessage;
            }

            public String getTriggerCompleteMessage() {
                return triggerCompleteMessage;
            }

            public void setTriggerCompleteMessage(String triggerCompleteMessage) {
                this.triggerCompleteMessage = triggerCompleteMessage;
            }
        }

        private static class LoggingJobHistoryPluginProperties {

            /**
             * org.quartz.plugins.history.LoggingJobHistoryPlugin <br>
             */
            @Value("${class}")
            private String className;

            /**
             * Job {1}.{0} fired (by trigger {4}.{3}) at: {2, date, HH:mm:ss MM/dd/yyyy} <br>
             */
            private String jobToBeFiredMessage;

            /**
             * Job {1}.{0} execution complete at {2, date, HH:mm:ss MM/dd/yyyy} and reports: {8} <br>
             */
            private String jobSuccessMessage;

            /**
             * Job {1}.{0} execution failed at {2, date, HH:mm:ss MM/dd/yyyy} and reports: {8} <br>
             */
            private String jobFailedMessage;

            /**
             * Job {1}.{0} was vetoed.  It was to be fired (by trigger {4}.{3}) at: {2, date, HH:mm:ss MM/dd/yyyy} <br>
             */
            private String jobWasVetoedMessage;

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getJobToBeFiredMessage() {
                return jobToBeFiredMessage;
            }

            public void setJobToBeFiredMessage(String jobToBeFiredMessage) {
                this.jobToBeFiredMessage = jobToBeFiredMessage;
            }

            public String getJobSuccessMessage() {
                return jobSuccessMessage;
            }

            public void setJobSuccessMessage(String jobSuccessMessage) {
                this.jobSuccessMessage = jobSuccessMessage;
            }

            public String getJobFailedMessage() {
                return jobFailedMessage;
            }

            public void setJobFailedMessage(String jobFailedMessage) {
                this.jobFailedMessage = jobFailedMessage;
            }

            public String getJobWasVetoedMessage() {
                return jobWasVetoedMessage;
            }

            public void setJobWasVetoedMessage(String jobWasVetoedMessage) {
                this.jobWasVetoedMessage = jobWasVetoedMessage;
            }
        }

        /**
         * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigPlugins.html
         *
         * http://www.quartz-scheduler.org/xml/job_scheduling_data_1_8.xsd
         */
        private static class XMLSchedulingDataProcessorPluginProperties {

            /**
             * org.quartz.plugins.xml.XMLSchedulingDataProcessorPlugin <br>
             */
            @Value("${class}")
            private String className;

            /**
             * data/my_job_data.xml <br>
             */
            private String fileNames;

            /**
             * true <br>
             */
            private Boolean failOnFileNotFound;

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getFileNames() {
                return fileNames;
            }

            public void setFileNames(String fileNames) {
                this.fileNames = fileNames;
            }

            public Boolean getFailOnFileNotFound() {
                return failOnFileNotFound;
            }

            public void setFailOnFileNotFound(Boolean failOnFileNotFound) {
                this.failOnFileNotFound = failOnFileNotFound;
            }
        }

        /**
         * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigPlugins.html
         */
        private static class ShutdownHookPluginProperties {

            /**
             * org.quartz.plugins.management.ShutdownHookPlugin <br>
             */
            @Value("${class}")
            private String className;

            /**
             * true <br>
             */
            private Boolean cleanShutdown;

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public Boolean getCleanShutdown() {
                return cleanShutdown;
            }

            public void setCleanShutdown(Boolean cleanShutdown) {
                this.cleanShutdown = cleanShutdown;
            }
        }

        private static class JobInterruptMonitorPluginProperties{

            /**
             * org.quartz.plugins.history.LoggingJobHistoryPlugin <br>
             */
            @Value("${class}")
            private String className;

            private String name;

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

}
