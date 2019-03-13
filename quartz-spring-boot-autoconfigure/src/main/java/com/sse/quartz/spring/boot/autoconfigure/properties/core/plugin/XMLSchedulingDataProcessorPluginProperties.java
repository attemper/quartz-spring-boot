package com.sse.quartz.spring.boot.autoconfigure.properties.core.plugin;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigPlugins.html
 *
 * http://www.quartz-scheduler.org/xml/job_scheduling_data_1_8.xsd
 */
public class XMLSchedulingDataProcessorPluginProperties {

    /**
     * data/my_job_data.xml <br>
     */
    private String fileNames;

    /**
     * true <br>
     */
    private Boolean failOnFileNotFound;

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
