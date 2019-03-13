package com.sse.quartz.spring.boot.autoconfigure.properties.core.plugin;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigPlugins.html
 */
public class LoggingTriggerHistoryPluginProperties {

    /**
     * Trigger {1}.{0} fired job {6}.{5} at: {4, date, HH:mm:ss MM/dd/yyyy} <br>
     */
    private String triggerFiredMessage;

    /**
     * Trigger {1}.{0} misfired job {6}.{5}  at: {4, date, HH:mm:ss MM/dd/yyyy}.  Should have fired at: {3, date, HH:mm:ss MM/dd/yyyy} <br>
     */
    private String triggerMisfiredMessage;

    /**
     * Trigger {1}.{0} completed firing job {6}.{5} at {4, date, HH:mm:ss MM/dd/yyyy} with resulting trigger instruction code: {9} <br>
     */
    private String triggerCompleteMessage;

    public String getTriggerFiredMessage() {
        return triggerFiredMessage;
    }

    public void setTriggerFiredMessage(String triggerFiredMessage) {
        this.triggerFiredMessage = triggerFiredMessage;
    }

    public String getTriggerMisfiredMessage() {
        return triggerMisfiredMessage;
    }

    public void setTriggerMisfiredMessage(String triggerMisfiredMessage) {
        this.triggerMisfiredMessage = triggerMisfiredMessage;
    }

    public String getTriggerCompleteMessage() {
        return triggerCompleteMessage;
    }

    public void setTriggerCompleteMessage(String triggerCompleteMessage) {
        this.triggerCompleteMessage = triggerCompleteMessage;
    }
}
