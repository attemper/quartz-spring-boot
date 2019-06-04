package com.github.attemper.quartz.spring.boot.autoconfigure.properties.scheduler;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigMain.html
 */
public class SchedulerProperties {

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

}
