package com.coherentlogic.coherent.datafeed.services;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.coherentlogic.coherent.datafeed.exceptions.TimeoutExpiredException;

/**
 * A class that can be used to pause a workflow until something happens, at
 * which point the resume method sends a signal to waiting threads, allowing
 * them to continue with their task.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class PauseResumeService {

    private final Lock lock = new ReentrantLock ();

    private final Condition hasCompletedCondition = lock.newCondition();

    private AtomicBoolean hasCompletedFlag = null;

    private final long time;

    private final TimeUnit timeUnit;

    /**
     * A flag the indicates whether the process completed successfully or that
     * it failed.
     *
     * The process may have completed and failed, which is why we need this.
     */
    private AtomicBoolean successFlag = null;

    /**
     * A default ctor that sets the timeout to 2 minutes.
     */
    public PauseResumeService () {
        this (5L, TimeUnit.MINUTES, false, false);
    }

    public PauseResumeService (
        long time,
        TimeUnit timeUnit,
        boolean hasCompleted,
        boolean successFlag
    ) {
        this.time = time;
        this.timeUnit = timeUnit;
        this.hasCompletedFlag = new AtomicBoolean (hasCompleted);
        this.successFlag = new AtomicBoolean (successFlag);
    }

    public PauseResumeService (
        long time,
        TimeUnit timeUnit,
        AtomicBoolean hasCompleted,
        AtomicBoolean successFlag
    ) {
        this.time = time;
        this.timeUnit = timeUnit;
        this.hasCompletedFlag = hasCompleted;
        this.successFlag = successFlag;
    }

    /**
     * A method that forces the thread to wait until the framework has been
     * initialised.
     *
     * Note that this method will return immediately when hasCompletedFlag is
     * true.
     * 
     * Also note that this method will only force the thread to wait for ten
     * seconds.
     *
     * @throws TimeoutExpiredException Wraps the InterruptedException and adds
     *  some additional information.
     *
     * @returns boolean True if the initialisation has completed successfully,
     *  false otherwise.
     */
    public boolean pause () {

        lock.lock();

        boolean result = hasCompletedFlag.get();

        try {
            if (!result)
                hasCompletedCondition.await(time, timeUnit);
        } catch (InterruptedException interruptedException) {
            throw new TimeoutExpiredException ("The thread waiting for the " +
                "hasCompletedCondition to be called has timed out.",
                interruptedException);
        } finally {
            lock.unlock();
        }

        return successFlag.get();
    }

    /**
     * Method sets the successFlag flag to the successFlag value and then sets
     * hasCompletedFlag to true and notifies all waiting threads.
     * 
     * Note that this method will only allow one thread at a time to change the
     * hasCompletedFlag flag and every change will notify any waiting threads
     * that the process has completed.
     *
     * Note that this method has protected access since it should only be called
     * from one of the initialisation methods.
     */
    public void resume (boolean successFlag) {

        lock.lock();

        setSuccessFlag (successFlag);
        this.hasCompletedFlag.set(true);

        hasCompletedCondition.signalAll();

        lock.unlock();
    }

    public boolean isSuccess() {
        return successFlag.get();
    }

    protected void setSuccessFlag(boolean successFlag) {
        this.successFlag.set(successFlag);
    }

    /**
     * Resets the successFlag and completed flags to false.
     */
    public void reset () {
        hasCompletedFlag.set(false);
        successFlag.set(false);
    }
}
