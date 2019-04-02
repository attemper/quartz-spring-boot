package com.sse.quartz.spring.boot.autoconfigure.properties;

import com.sse.quartz.spring.boot.autoconfigure.constant.ConfigConst;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "")
public class SseExtraProperties {

    /**
     * org.quartz.simpl.SimpleInstanceIdGenerator <br>
     *
     * Only used if org.quartz.scheduler.instanceId is set to “AUTO”.
     * Defaults to “org.quartz.simpl.SimpleInstanceIdGenerator”, which generates an instance id based upon host name and time stamp.
     * Other IntanceIdGenerator implementations include SystemPropertyInstanceIdGenerator (which gets the instance id from the system property “org.quartz.scheduler.instanceId”, and HostnameInstanceIdGenerator which uses the local host name (InetAddress.getLocalHost().getHostName()).
     * You can also implement the InstanceIdGenerator interface your self.
     */
    @Value("${" + StdSchedulerFactory.PROP_SCHED_INSTANCE_ID_GENERATOR_CLASS + ":}")
    private String instanceIdGeneratorClass;

    /**
     * org.quartz.simpl.CascadingClassLoadHelper <br>
     *
     * Defaults to the most robust approach, which is to use the “org.quartz.simpl.CascadingClassLoadHelper” class - which in turn uses every other ClassLoadHelper class until one works.
     * You should probably not find the need to specify any other class for this property, though strange things seem to happen within application servers.
     * All of the current possible ClassLoadHelper implementation can be found in the org.quartz.simpl package.
     */
    @Value("${" + StdSchedulerFactory.PROP_SCHED_CLASS_LOAD_HELPER_CLASS + ":}")
    private String classLoadHelperClass;

    /**
     * org.quartz.simpl.PropertySettingJobFactory <br>
     *
     * The class name of the JobFactory to use. A JobFatcory is responsible for producing instances of JobClasses.
     * The default is ‘org.quartz.simpl.PropertySettingJobFactory’, which simply calls newInstance() on the class to produce a new instance each time execution is about to occur.
     * PropertySettingJobFactory also reflectively sets the job’s bean properties using the contents of the SchedulerContext and Job and Trigger JobDataMaps.
     */
    @Value("${" + StdSchedulerFactory.PROP_SCHED_JOB_FACTORY_CLASS + ":}")
    private String jobFactoryClass;

    /**
     * The class name to be used to produce an instance of a org.quartz.impl.jdbcjobstore.Semaphore to be used for locking control on the job store data.
     * This is an advanced configuration feature, which should not be used by most users.
     * By default, Quartz will select the most appropriate (pre-bundled) Semaphore implementation to use.
     * “org.quartz.impl.jdbcjobstore.UpdateLockRowSemaphore” QUARTZ-497 may be of interest to MS SQL Server users. See QUARTZ-441.
     *
     * “JTANonClusteredSemaphore” which is bundled with Quartz may give improved performance when using JobStoreCMT, though it is an experimental implementation. See QUARTZ-441 and QUARTZ-442
     */
    @Value("${" + StdSchedulerFactory.PROP_JOB_STORE_LOCK_HANDLER_CLASS + ":}")
    private String lockHandlerClass;

    /**
     * org.quartz.simpl.SimpleThreadPool <br>
     *
     * Is the name of the ThreadPool implementation you wish to use.
     * The threadpool that ships with Quartz is “org.quartz.simpl.SimpleThreadPool”, and should meet the needs of nearly every user.
     * It has very simple behavior and is very well tested.
     * It provides a fixed-size pool of threads that ‘live’ the lifetime of the Scheduler.
     */
    @Value("${" + StdSchedulerFactory.PROP_THREAD_POOL_CLASS + ":}")
    private String threadPoolClass;

    /**
     * @see org.quartz.simpl.RAMJobStore <br>
     * @see org.quartz.impl.jdbcjobstore.JobStoreTX <br>
     * @see org.quartz.impl.jdbcjobstore.JobStoreCMT <br>
     */
    @Value("${" + StdSchedulerFactory.PROP_JOB_STORE_CLASS + ":}")
    private String jobStoreClass;

    /**
     * org.quartz.plugins.history.LoggingJobHistoryPlugin <br>
     */
    @Value("${" + ConfigConst.PROP_PLUGIN_JOB_HISTORY_CLASS + ":}")
    private String jobHistoryClass;

    /**
     * org.quartz.plugins.history.LoggingTriggerHistoryPlugin <br>
     */
    @Value("${" + ConfigConst.PROP_PLUGIN_TRIGGER_HISTORY_CLASS + ":}")
    private String triggerHistoryClass;

    /**
     * org.quartz.plugins.xml.XMLSchedulingDataProcessorPlugin <br>
     */
    @Value("${" + ConfigConst.PROP_PLUGIN_JOB_INITIALIZER_CLASS + ":}")
    private String jobInitializerClass;

    /**
     * org.quartz.plugins.management.ShutdownHookPlugin <br>
     */
    @Value("${" + ConfigConst.PROP_PLUGIN_SHUTDOWNHOOK_CLASS + ":}")
    private String shutdownhookClass;

    /**
     * org.quartz.plugins.interrupt.JobInterruptMonitorPlugin <br>
     */
    @Value("${" + ConfigConst.PROP_PLUGIN_JOB_INTERRUPT_MONITOR_CLASS + ":}")
    private String jobInterruptMonitorClass;

    public String getInstanceIdGeneratorClass() {
        return instanceIdGeneratorClass;
    }

    public void setInstanceIdGeneratorClass(String instanceIdGeneratorClass) {
        this.instanceIdGeneratorClass = instanceIdGeneratorClass;
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

    public String getLockHandlerClass() {
        return lockHandlerClass;
    }

    public void setLockHandlerClass(String lockHandlerClass) {
        this.lockHandlerClass = lockHandlerClass;
    }

    public String getThreadPoolClass() {
        return threadPoolClass;
    }

    public void setThreadPoolClass(String threadPoolClass) {
        this.threadPoolClass = threadPoolClass;
    }

    public String getJobStoreClass() {
        return jobStoreClass;
    }

    public void setJobStoreClass(String jobStoreClass) {
        this.jobStoreClass = jobStoreClass;
    }

    public String getJobHistoryClass() {
        return jobHistoryClass;
    }

    public void setJobHistoryClass(String jobHistoryClass) {
        this.jobHistoryClass = jobHistoryClass;
    }

    public String getTriggerHistoryClass() {
        return triggerHistoryClass;
    }

    public void setTriggerHistoryClass(String triggerHistoryClass) {
        this.triggerHistoryClass = triggerHistoryClass;
    }

    public String getJobInitializerClass() {
        return jobInitializerClass;
    }

    public void setJobInitializerClass(String jobInitializerClass) {
        this.jobInitializerClass = jobInitializerClass;
    }

    public String getShutdownhookClass() {
        return shutdownhookClass;
    }

    public void setShutdownhookClass(String shutdownhookClass) {
        this.shutdownhookClass = shutdownhookClass;
    }

    public String getJobInterruptMonitorClass() {
        return jobInterruptMonitorClass;
    }

    public void setJobInterruptMonitorClass(String jobInterruptMonitorClass) {
        this.jobInterruptMonitorClass = jobInterruptMonitorClass;
    }
}
