package com.sse.quartz.spring.boot.autoconfigure.properties.core.plugin;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigPlugins.html
 */
public class ShutdownHookPluginProperties {

    /**
     * true <br>
     */
    private Boolean cleanShutdown;

    public Boolean getCleanShutdown() {
        return cleanShutdown;
    }

    public void setCleanShutdown(Boolean cleanShutdown) {
        this.cleanShutdown = cleanShutdown;
    }
}
