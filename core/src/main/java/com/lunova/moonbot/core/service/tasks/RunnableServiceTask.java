package com.lunova.moonbot.core.service.tasks;

import com.lunova.moonbot.core.service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public abstract class RunnableServiceTask extends ServiceTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RunnableServiceTask.class);

    public RunnableServiceTask(TaskPriority taskPriority, Service<?> originator) {
        super(taskPriority, originator);
    }

    @Override
    public void run() {
        try {
            MDC.put("threadName", Thread.currentThread().getName());
            setStartTime(System.currentTimeMillis());
            setTaskState(TaskState.RUNNING);
            onRun();
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
    }

    protected abstract void onRun();
}
