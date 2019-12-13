package com.github.attemper.quartz.spring.boot.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.attemper.quartz.spring.boot.autoconfigure.constant.ConfigConst;
import com.github.attemper.quartz.spring.boot.autoconfigure.db.DataSourceHolder;
import com.github.attemper.quartz.spring.boot.autoconfigure.jobstore.CustomJobStoreCMT;
import com.github.attemper.quartz.spring.boot.autoconfigure.properties.ExtraProperties;
import com.github.attemper.quartz.spring.boot.autoconfigure.properties.QuartzProperties;
import com.github.attemper.quartz.spring.boot.autoconfigure.properties.plugin.PluginProperties;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Quartz Scheduler.
 */
@Configuration
@EnableConfigurationProperties({QuartzProperties.class, ExtraProperties.class})
@AutoConfigureAfter({ JacksonAutoConfiguration.class, DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class })
public class CustomQuartzAutoConfiguration {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ExtraProperties extraProperties;


	@Bean
	@ConditionalOnMissingBean
    public SchedulerFactory schedulerFactory(QuartzProperties quartzProperties, DataSource dataSource,
                                             @QuartzDataSource ObjectProvider<DataSource> quartzDataSource)
            throws SchedulerException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        preHandleParam();
        StdSchedulerFactory factory = new StdSchedulerFactory();
        initDataSource(quartzProperties, dataSource, quartzDataSource); // init dataSource if needed
        initQuartzProperties(factory, quartzProperties); // init properties
        return factory;
    }

    /**
     * if the jobStore is not RAMJobStore and the dataSource is null, then try to set the dataSource by inject spring bean
     *
     * @param quartzProperties
     * @param dataSource
     * @param quartzDataSource
     */
    private void initDataSource(QuartzProperties quartzProperties, DataSource dataSource, ObjectProvider<DataSource> quartzDataSource)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (Class.forName(extraProperties.getJobStoreClass()).newInstance() instanceof CustomJobStoreCMT
                && quartzProperties.getDataSource().isEmpty()) {
            //use spring-boot datasource
            DataSource dataSourceToUse = getDataSource(dataSource, quartzDataSource);
            DataSourceHolder.set(dataSourceToUse);
        }
    }

    /**
     * it means that you can customize your datasource by using <code>@QuartzDatasource</code>
     *
     * @param dataSource
     * @param quartzDataSource
     * @return
     */
    private DataSource getDataSource(DataSource dataSource,
                                     ObjectProvider<DataSource> quartzDataSource) {
        DataSource dataSourceIfAvailable = quartzDataSource.getIfAvailable();
        return (dataSourceIfAvailable != null) ? dataSourceIfAvailable : dataSource;
    }

    private void initQuartzProperties(StdSchedulerFactory factory, QuartzProperties quartzProperties) throws SchedulerException {
        Properties properties = new Properties();
        properties.putAll(asPropertiesMap(quartzProperties));
        factory.initialize(properties);
    }

    private void preHandleParam() {
        if (StringUtils.isEmpty(extraProperties.getJobStoreClass())) {
            extraProperties.setJobStoreClass(CustomJobStoreCMT.class.getName());
        }
    }

	private Map<String, String> asPropertiesMap(QuartzProperties quartzProperties) {
        Map<String, String> map = new HashMap<>();

        putObj2Map(map, quartzProperties.getScheduler(), "scheduler");
        map.remove(ConfigConst.ROOT_NAMESPACE_QUARTZ + ".scheduler.rmi");
        if (quartzProperties.getScheduler().getRmi() != null) {
            putObj2Map(map, quartzProperties.getScheduler().getRmi(), "scheduler.rmi");
        }

        putObj2Map(map, quartzProperties.getJobStore(), "jobStore");

        putObj2Map(map, quartzProperties.getThreadPool(), "threadPool");

        putMapWithKey(map, quartzProperties.getDataSource(), "dataSource");

        putMapWithKey(map, quartzProperties.getJobListener(), "jobListener");

        putMapWithKey(map, quartzProperties.getTriggerListener(), "triggerListener");

        PluginProperties pluginProperties = quartzProperties.getPlugin();
        if (pluginProperties.getJobHistory() != null) {
            putObj2Map(map, pluginProperties.getJobHistory(), "plugin.jobHistory");
        }
        if (pluginProperties.getTriggerHistory() != null) {
            putObj2Map(map, pluginProperties.getTriggerHistory(), "plugin.triggerHistory");
        }
        if (pluginProperties.getJobInitializer() != null) {
            putObj2Map(map, pluginProperties.getJobInitializer(), "plugin.jobInitializer");
        }
        if (pluginProperties.getShutdownhook() != null) {
            putObj2Map(map, pluginProperties.getShutdownhook(), "plugin.shutdownhook");
        }
        if (pluginProperties.getJobInterruptMonitor() != null) {
            putObj2Map(map, pluginProperties.getJobInterruptMonitor(), "plugin.jobInterruptMonitor");
        }

        if (quartzProperties.getContext().getKey() != null) {
			putMapWithKey(map, quartzProperties.getContext().getKey(), "context.key");
		}

		addExtraProps(map);

		return map;
	}

	private void addExtraProps(Map<String, String> map) {
		putValue2Map(map, StdSchedulerFactory.PROP_SCHED_INSTANCE_ID_GENERATOR_CLASS, extraProperties.getInstanceIdGeneratorClass());
		putValue2Map(map, StdSchedulerFactory.PROP_SCHED_CLASS_LOAD_HELPER_CLASS, extraProperties.getClassLoadHelperClass());
		putValue2Map(map, StdSchedulerFactory.PROP_SCHED_JOB_FACTORY_CLASS, extraProperties.getJobFactoryClass());
		putValue2Map(map, StdSchedulerFactory.PROP_JOB_STORE_LOCK_HANDLER_CLASS, extraProperties.getLockHandlerClass());
		putValue2Map(map, StdSchedulerFactory.PROP_THREAD_POOL_CLASS, extraProperties.getThreadPoolClass());
		putValue2Map(map, StdSchedulerFactory.PROP_JOB_STORE_CLASS, extraProperties.getJobStoreClass());

		putValue2Map(map, ConfigConst.PROP_PLUGIN_JOB_HISTORY_CLASS, extraProperties.getJobHistoryClass());
		putValue2Map(map, ConfigConst.PROP_PLUGIN_TRIGGER_HISTORY_CLASS, extraProperties.getTriggerHistoryClass());
		putValue2Map(map, ConfigConst.PROP_PLUGIN_JOB_INITIALIZER_CLASS, extraProperties.getJobInitializerClass());
		putValue2Map(map, ConfigConst.PROP_PLUGIN_SHUTDOWNHOOK_CLASS, extraProperties.getShutdownhookClass());
		putValue2Map(map, ConfigConst.PROP_PLUGIN_JOB_INTERRUPT_MONITOR_CLASS, extraProperties.getJobInterruptMonitorClass());

	}

	private void putObj2Map(Map<String, String> map, Object sourceObject, String key) {
		putMapWithKey(map, obj2Map(sourceObject), key);
	}

	private Map<String, String> obj2Map(Object sourceObject) {
		try {
			return objectMapper.readValue(objectMapper.writeValueAsString(sourceObject), Map.class);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private void putMapWithKey(Map<String, String> map, Map<String, ?> tempMap, String key) {
		for (Map.Entry<String, ?> entry : tempMap.entrySet()) {
			if (entry.getValue() != null) {
				map.put(ConfigConst.ROOT_NAMESPACE_QUARTZ + "." + key + "." + entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
	}

	private void putValue2Map(Map<String, String> map, String key, Object value) {
		if (!StringUtils.isEmpty(value)) {
			map.put(key, String.valueOf(value));
		}
	}
}
