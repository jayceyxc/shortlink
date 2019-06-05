package com.bcdata.shortlink.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * @author yuxuecheng
 * @version 1.0
 * @contact yuxuecheng@baicdata.com
 * @time 2018 June 01 11:36
 */
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    private static final Logger logger = LoggerFactory.getLogger (MyThreadPoolExecutor.class);

    public MyThreadPoolExecutor (int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super (corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute (Runnable r, Throwable t) {
        super.afterExecute (r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                Object result = ((Future<?>) r).get ();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause ();
            } catch (InterruptedException ie) {
                Thread.currentThread ().interrupt (); // ignore/reset
            }
        }
        if (t != null) {
            // Exception occurred
            logger.error("Uncaught exception is detected! " + t
                    + " st: " + Arrays.toString(t.getStackTrace()));
            logger.error ("error message: " + t.getMessage ());
            // ... Handle the exception
            logger.error("Restart the runnable again" + r.getClass ());
            execute (r);
        }
    }
}
