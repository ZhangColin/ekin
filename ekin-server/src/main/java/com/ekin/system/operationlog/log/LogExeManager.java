package com.ekin.system.operationlog.log;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author colin
 */
public class LogExeManager {
    private static final int LOG_DELAY_TIME = 10;

    private ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("log-pool-%d").build();

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(20, threadFactory);

    private static LogExeManager logExeManager = new LogExeManager();

    private LogExeManager() {
    }

    public static LogExeManager getInstance(){
        return logExeManager;
    }

    public void executeLogTask(TimerTask timerTask) {
        executor.schedule(timerTask, LOG_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
