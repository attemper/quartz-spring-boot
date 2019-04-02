package com.sse.quartz.spring.boot.autoconfigure.properties.plugin;

public class LoggingJobHistoryPluginProperties {

    /**
     * Job {1}.{0} fired (by trigger {4}.{3}) at: {2, date, HH:mm:ss MM/dd/yyyy} <br>
     */
    private String jobToBeFiredMessage;

    /**
     * Job {1}.{0} execution complete at {2, date, HH:mm:ss MM/dd/yyyy} and reports: {8} <br>
     */
    private String jobSuccessMessage;

    /**
     * Job {1}.{0} execution failed at {2, date, HH:mm:ss MM/dd/yyyy} and reports: {8} <br>
     */
    private String jobFailedMessage;

    /**
     * Job {1}.{0} was vetoed.  It was to be fired (by trigger {4}.{3}) at: {2, date, HH:mm:ss MM/dd/yyyy} <br>
     */
    private String jobWasVetoedMessage;

    public String getJobToBeFiredMessage() {
        return jobToBeFiredMessage;
    }

    public void setJobToBeFiredMessage(String jobToBeFiredMessage) {
        this.jobToBeFiredMessage = jobToBeFiredMessage;
    }

    public String getJobSuccessMessage() {
        return jobSuccessMessage;
    }

    public void setJobSuccessMessage(String jobSuccessMessage) {
        this.jobSuccessMessage = jobSuccessMessage;
    }

    public String getJobFailedMessage() {
        return jobFailedMessage;
    }

    public void setJobFailedMessage(String jobFailedMessage) {
        this.jobFailedMessage = jobFailedMessage;
    }

    public String getJobWasVetoedMessage() {
        return jobWasVetoedMessage;
    }

    public void setJobWasVetoedMessage(String jobWasVetoedMessage) {
        this.jobWasVetoedMessage = jobWasVetoedMessage;
    }
}
