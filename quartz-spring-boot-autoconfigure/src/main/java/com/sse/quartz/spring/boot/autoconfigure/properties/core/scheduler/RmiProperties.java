package com.sse.quartz.spring.boot.autoconfigure.properties.core.scheduler;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigRMI.html
 */
public class RmiProperties {
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
