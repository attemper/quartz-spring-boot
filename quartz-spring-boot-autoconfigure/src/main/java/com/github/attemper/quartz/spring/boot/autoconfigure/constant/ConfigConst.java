package com.github.attemper.quartz.spring.boot.autoconfigure.constant;

import org.quartz.impl.StdSchedulerFactory;

public interface ConfigConst {

    String ROOT_NAMESPACE_QUARTZ = "org.quartz";

    String PROP_PLUGIN_JOB_HISTORY_CLASS = StdSchedulerFactory.PROP_PLUGIN_PREFIX + ".jobHistory." + StdSchedulerFactory.PROP_PLUGIN_CLASS;

    String PROP_PLUGIN_TRIGGER_HISTORY_CLASS = StdSchedulerFactory.PROP_PLUGIN_PREFIX + ".triggerHistory." + StdSchedulerFactory.PROP_PLUGIN_CLASS;

    String PROP_PLUGIN_JOB_INITIALIZER_CLASS = StdSchedulerFactory.PROP_PLUGIN_PREFIX + ".jobInitializer." + StdSchedulerFactory.PROP_PLUGIN_CLASS;

    String PROP_PLUGIN_SHUTDOWNHOOK_CLASS = StdSchedulerFactory.PROP_PLUGIN_PREFIX + ".shutdownhook." + StdSchedulerFactory.PROP_PLUGIN_CLASS;

    String PROP_PLUGIN_JOB_INTERRUPT_MONITOR_CLASS = StdSchedulerFactory.PROP_PLUGIN_PREFIX + ".jobInterruptMonitor." + StdSchedulerFactory.PROP_PLUGIN_CLASS;
}
