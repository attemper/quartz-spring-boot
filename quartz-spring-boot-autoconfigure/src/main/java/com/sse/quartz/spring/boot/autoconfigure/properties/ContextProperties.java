package com.sse.quartz.spring.boot.autoconfigure.properties;

import java.util.HashMap;
import java.util.Map;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigMain.html
 */
public class ContextProperties {

    /**
     * Represent a name-value pair that will be placed into the “scheduler context” as strings.
     * (see Scheduler.getContext()). So for example, the setting “org.quartz.context.key.MyKey = MyValue” would perform the equivalent of scheduler.getContext().put(“MyKey”, “MyValue”).
     * The Transaction-Related properties should be left out of the config file unless you are using JTA transactions.
     */
    private Map<String, String> key = new HashMap<>();

    public Map<String, String> getKey() {
        return key;
    }

    public void setKey(Map<String, String> key) {
        this.key = key;
    }
}
