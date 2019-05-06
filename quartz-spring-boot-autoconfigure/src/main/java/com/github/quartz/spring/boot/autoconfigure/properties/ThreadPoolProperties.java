package com.github.quartz.spring.boot.autoconfigure.properties;

/**
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/configuration/ConfigThreadPool.html
 */
public class ThreadPoolProperties {

    /**
     * 30 <br>
     *
     * Can be any positive integer, although you should realize that only numbers between 1 and 100 are very practical.
     * This is the number of threads that are available for concurrent execution of jobs.
     * If you only have a few jobs that fire a few times a day, then 1 thread is plenty!
     * If you have tens of thousands of jobs, with many firing every minute, then you probably want a thread count more like 50 or 100 (this highly depends on the nature of the work that your jobs perform, and your systems resources!).
     */
    private Integer threadCount = 30;

    /**
     * Thread.NORM_PRIORITY (5) <br>
     *
     * Can be any int between Thread.MIN_PRIORITY (which is 1) and Thread.MAX_PRIORITY (which is 10).
     * The default is Thread.NORM_PRIORITY (5).
     *
     */
    private Integer threadPriority;

    /**
     * false <br>
     *
     * Can be set to “true” to have the threads in the pool created as daemon threads.
     * Default is “false”. See also the org.quartz.scheduler.makeSchedulerThreadDaemon property.
     */
    private Boolean makeThreadsDaemons;

    /**
     * true <br>
     *
     * Can be “true” or “false”, and defaults to true.
     */
    private Boolean threadsInheritGroupOfInitializingThread;

    /**
     * false <br>
     *
     * Can be “true” or “false”, and defaults to false.
     */
    private Boolean threadsInheritContextClassLoaderOfInitializingThread;

    /**
     * [Scheduler Name]_Worker <br>
     *
     * The prefix for thread names in the worker pool - will be postpended with a number.
     */
    private String threadNamePrefix;

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public Integer getThreadPriority() {
        return threadPriority;
    }

    public void setThreadPriority(Integer threadPriority) {
        this.threadPriority = threadPriority;
    }

    public Boolean getMakeThreadsDaemons() {
        return makeThreadsDaemons;
    }

    public void setMakeThreadsDaemons(Boolean makeThreadsDaemons) {
        this.makeThreadsDaemons = makeThreadsDaemons;
    }

    public Boolean getThreadsInheritGroupOfInitializingThread() {
        return threadsInheritGroupOfInitializingThread;
    }

    public void setThreadsInheritGroupOfInitializingThread(Boolean threadsInheritGroupOfInitializingThread) {
        this.threadsInheritGroupOfInitializingThread = threadsInheritGroupOfInitializingThread;
    }

    public Boolean getThreadsInheritContextClassLoaderOfInitializingThread() {
        return threadsInheritContextClassLoaderOfInitializingThread;
    }

    public void setThreadsInheritContextClassLoaderOfInitializingThread(Boolean threadsInheritContextClassLoaderOfInitializingThread) {
        this.threadsInheritContextClassLoaderOfInitializingThread = threadsInheritContextClassLoaderOfInitializingThread;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }
}
