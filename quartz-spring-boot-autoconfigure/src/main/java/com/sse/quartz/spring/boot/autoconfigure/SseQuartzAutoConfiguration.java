/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sse.quartz.spring.boot.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sse.quartz.spring.boot.autoconfigure.constant.ConfigConst;
import com.sse.quartz.spring.boot.autoconfigure.properties.core.SseExtraProperties;
import com.sse.quartz.spring.boot.autoconfigure.properties.core.SseQuartzProperties;
import com.sse.quartz.spring.boot.autoconfigure.properties.core.plugin.PluginProperties;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.simpl.RAMJobStore;
import org.quartz.utils.ConnectionProvider;
import org.quartz.utils.DBConnectionManager;
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
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Quartz Scheduler.
 */
@Configuration
@EnableConfigurationProperties({SseQuartzProperties.class, SseExtraProperties.class})
@AutoConfigureAfter({ JacksonAutoConfiguration.class, DataSourceAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class })
public class SseQuartzAutoConfiguration {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SseExtraProperties sseExtraProperties;

	private static final String DATASOURCE_NAME = "sseQuartzDB";

	@Bean
	@ConditionalOnMissingBean
	public SchedulerFactory schedulerFactory(SseQuartzProperties sseProperties, DataSource dataSource,
											 @QuartzDataSource ObjectProvider<DataSource> quartzDataSource) throws SchedulerException {
		StdSchedulerFactory factory = new StdSchedulerFactory();
		initDataSource(sseProperties, dataSource, quartzDataSource); // init dataSource if needed
		initSseQuartzProperties(factory, sseProperties); // init properties
		return factory;
	}

	/**
	 * if the jobStore is not RAMJobStore and the dataSource is null, then try to set the dataSource by inject spring bean
	 * @param sseProperties
	 * @param dataSource
	 * @param quartzDataSource
	 */
	private void initDataSource(SseQuartzProperties sseProperties, DataSource dataSource, ObjectProvider<DataSource> quartzDataSource) {
		if (!RAMJobStore.class.getName().equals(sseExtraProperties.getJobStoreClass())
				&& sseProperties.getDataSource().isEmpty()) {
			sseProperties.getJobStore().setDataSource(DATASOURCE_NAME);
			//use spring-boot datasource
			DataSource dataSourceToUse = getDataSource(dataSource, quartzDataSource);
			DBConnectionManager.getInstance().addConnectionProvider(DATASOURCE_NAME, new ConnectionProvider() {
				@Override
				public Connection getConnection() throws SQLException {
					return DataSourceUtils.getConnection(dataSourceToUse);
				}

				@Override
				public void shutdown() throws SQLException {
				}

				@Override
				public void initialize() throws SQLException {
				}
			});
		}
	}

	/**
	 * it means that you can customize your datasource by using <code>@QuartzDatasource</code>
	 * @param dataSource
	 * @param quartzDataSource
	 * @return
	 */
	private DataSource getDataSource(DataSource dataSource,
									 ObjectProvider<DataSource> quartzDataSource) {
		DataSource dataSourceIfAvailable = quartzDataSource.getIfAvailable();
		return (dataSourceIfAvailable != null) ? dataSourceIfAvailable : dataSource;
	}

	private void initSseQuartzProperties(StdSchedulerFactory factory, SseQuartzProperties sseProperties) throws SchedulerException {
		Properties properties = new Properties();
		properties.putAll(asSsePropertiesMap(sseProperties));
		factory.initialize(properties);
	}

	private Map<String, String> asSsePropertiesMap(SseQuartzProperties sseProperties) {
		Map<String, String> map = new HashMap<>();

		putObj2Map(map, sseProperties.getScheduler(), "scheduler");
		map.remove(ConfigConst.ROOT_NAMESPACE_QUARTZ + ".scheduler.rmi");
		if (sseProperties.getScheduler().getRmi() != null) {
			putObj2Map(map, sseProperties.getScheduler().getRmi(), "scheduler.rmi");
		}

		putObj2Map(map, sseProperties.getJobStore(), "jobStore");

		putObj2Map(map, sseProperties.getThreadPool(), "threadPool");

		putMapWithKey(map, sseProperties.getDataSource(), "dataSource");

		putMapWithKey(map, sseProperties.getJobListener(), "jobListener");

		putMapWithKey(map, sseProperties.getTriggerListener(), "triggerListener");

		PluginProperties pluginProperties = sseProperties.getPlugin();
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

		if (sseProperties.getContext().getKey() != null) {
			putMapWithKey(map, sseProperties.getContext().getKey(), "context.key");
		}

		addExtraProps(map);

		return map;
	}

	private void addExtraProps(Map<String, String> map) {
		putValue2Map(map, StdSchedulerFactory.PROP_SCHED_INSTANCE_ID_GENERATOR_CLASS, sseExtraProperties.getInstanceIdGeneratorClass());
		putValue2Map(map, StdSchedulerFactory.PROP_SCHED_CLASS_LOAD_HELPER_CLASS, sseExtraProperties.getClassLoadHelperClass());
		putValue2Map(map, StdSchedulerFactory.PROP_SCHED_JOB_FACTORY_CLASS, sseExtraProperties.getJobFactoryClass());
		putValue2Map(map, StdSchedulerFactory.PROP_JOB_STORE_LOCK_HANDLER_CLASS, sseExtraProperties.getLockHandlerClass());
		putValue2Map(map, StdSchedulerFactory.PROP_THREAD_POOL_CLASS, sseExtraProperties.getThreadPoolClass());
		putValue2Map(map, StdSchedulerFactory.PROP_JOB_STORE_CLASS, sseExtraProperties.getJobStoreClass());

		putValue2Map(map, ConfigConst.PROP_PLUGIN_JOB_HISTORY_CLASS, sseExtraProperties.getJobHistoryClass());
		putValue2Map(map, ConfigConst.PROP_PLUGIN_TRIGGER_HISTORY_CLASS, sseExtraProperties.getTriggerHistoryClass());
		putValue2Map(map, ConfigConst.PROP_PLUGIN_JOB_INITIALIZER_CLASS, sseExtraProperties.getJobInitializerClass());
		putValue2Map(map, ConfigConst.PROP_PLUGIN_SHUTDOWNHOOK_CLASS, sseExtraProperties.getShutdownhookClass());
		putValue2Map(map, ConfigConst.PROP_PLUGIN_JOB_INTERRUPT_MONITOR_CLASS, sseExtraProperties.getJobInterruptMonitorClass());

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
