package com.lunova.moonbot.core.service.tasks;

import com.google.common.base.Optional;
import com.lunova.moonbot.core.service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.Callable;

public abstract class CallableServiceTask<T> extends ServiceTask implements Callable<T> {

    private static final Logger logger = LoggerFactory.getLogger(CallableServiceTask.class);

    private T result;

    public CallableServiceTask(TaskPriority taskPriority, Service<?> originator) {
        super(taskPriority, originator);
    }

    public Optional<T> getResult() {
        return Optional.fromNullable(result);
    }

    @Override
    public T call() throws Exception {
        try {
            MDC.put("threadName", Thread.currentThread().getName());
            setStartTime(System.currentTimeMillis());
            setTaskState(TaskState.RUNNING);
            result = onCall();
            setTaskState(TaskState.COMPLETED);
        } catch (RuntimeException e) {
            setTaskState(TaskState.COMPLETED_WITH_ERRORS);
        } catch (Exception e) {
            setTaskState(TaskState.FAILED);
        } finally {
            setCompleteTime(System.currentTimeMillis());
            StringBuilder logEntry = new StringBuilder();
            logEntry.append("Task Details - ")
                    .append("ID: ")
                    .append(this.getTaskId())
                    .append(" Priority: ")
                    .append(this.getTaskPriority())
                    .append(", Originator: ")
                    .append(this.getOriginator().getName())
                    .append(", State: ")
                    .append(this.getTaskState())
                    .append(", Submission Time: ")
                    .append(this.getSubmissionTime())
                    .append(", Start Time: ")
                    .append(this.getStartTime())
                    .append(", Complete Time: ")
                    .append(this.getCompleteTime());
            // logger.debug(logEntry.toString());
        }
        return result;
    }

    protected abstract T onCall() throws Exception;
}
