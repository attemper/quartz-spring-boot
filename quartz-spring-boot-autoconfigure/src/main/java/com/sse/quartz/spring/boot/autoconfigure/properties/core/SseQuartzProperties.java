package com.sse.quartz.spring.boot.autoconfigure.properties.core;

import com.sse.quartz.spring.boot.autoconfigure.constant.ConfigConst;
import com.sse.quartz.spring.boot.autoconfigure.properties.core.plugin.PluginProperties;
import com.sse.quartz.spring.boot.autoconfigure.properties.core.scheduler.SchedulerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(ConfigConst.ROOT_NAMESPACE_QUARTZ)
public class SseQuartzProperties {

    private final SchedulerProperties scheduler = new SchedulerProperties();

    private final JobStoreProperties jobStore = new JobStoreProperties();

    private final ThreadPoolProperties threadPool = new ThreadPoolProperties();

    /**
     * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigDataSources.html
     */
    private final Map<String, Object> dataSource = new HashMap<>();

    /**
     * key = value like <br>
     * NAME.class = com.foo.MyListenerClass
     * NAME.propName = propValue
     * NAME.prop2Name = prop2Value
     */
    private final Map<String, String> jobListener = new HashMap<>();

    /**
     * key = value like <br>
     * NAME.class = com.foo.MyListenerClass
     * NAME.propName = propValue
     * NAME.prop2Name = prop2Value
     */
    private final Map<String, String> triggerListener = new HashMap<>();

    private final PluginProperties plugin = new PluginProperties();

    private final ContextProperties context = new ContextProperties();

    public SchedulerProperties getScheduler() {
        return scheduler;
    }

    public ContextProperties getContext() {
        return context;
    }

    public ThreadPoolProperties getThreadPool() {
        return threadPool;
    }

    public Map<String, String> getTriggerListener() {
        return triggerListener;
    }

    public Map<String, String> getJobListener() {
        return jobListener;
    }

    public PluginProperties getPlugin() {
        return plugin;
    }

    public JobStoreProperties getJobStore() {
        return jobStore;
    }

    public Map<String, Object> getDataSource() {
        return dataSource;
    }
}
