package com.sse.quartz.spring.boot.autoconfigure.properties.core.plugin;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigPlugins.html
 */
public class PluginProperties {

    private final LoggingJobHistoryPluginProperties jobHistory = new LoggingJobHistoryPluginProperties();

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
}
