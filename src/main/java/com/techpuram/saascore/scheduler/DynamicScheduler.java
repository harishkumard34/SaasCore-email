package com.techpuram.saascore.scheduler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class DynamicScheduler {

    private final ApplicationContext applicationContext;
    private final TaskScheduler taskScheduler;
    private final SchedulerRepo schedulerRepo;

    public DynamicScheduler(@Lazy ApplicationContext applicationContext, SchedulerRepo schedulerRepo) {
        this.schedulerRepo = schedulerRepo;
        this.applicationContext = applicationContext;
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.initialize();
        this.taskScheduler = threadPoolTaskScheduler;
    }

    public void executeTask(Scheduler scheduler) {
        try {
            if ("one time".equalsIgnoreCase(scheduler.getLoopType())) {
                scheduleOneTimeTask(scheduler);
            } else if ("repeated".equalsIgnoreCase(scheduler.getLoopType())) {
                scheduleRepeatedTask(scheduler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleOneTimeTask(Scheduler scheduler) {
        LocalDateTime scheduleTime = scheduler.getScheduleTime();
        ScheduledFuture<?> future = taskScheduler.schedule(() -> runTask(scheduler), scheduleTime.atZone(ZoneId.systemDefault()).toInstant());
        scheduledTasks.put(scheduler.getTaskId(), future);
    }

    private void scheduleRepeatedTask(Scheduler scheduler) {
        String cronExpression = scheduler.getCronExpression();
        ScheduledFuture<?> future = taskScheduler.schedule(() -> runTask(scheduler), new CronTrigger(cronExpression));
        scheduledTasks.put(scheduler.getTaskId(), future);

    }

    private void runTask(Scheduler scheduler) {
        try {
            String className = scheduler.getClassName();
            Class<?> clazz = Class.forName(className);
            SchedulerInterface task = (SchedulerInterface) applicationContext.getBean(clazz);
            task.runTask(scheduler);
            scheduler.setExecutedTime(System.currentTimeMillis());
            if ("one time".equalsIgnoreCase(scheduler.getLoopType())) {
                scheduler.setStatusName("completed");
                schedulerRepo.save(scheduler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Map<Integer, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    public void cancelTask(Integer taskId) {
        ScheduledFuture<?> future = scheduledTasks.get(taskId);
        if (future != null) {
            future.cancel(true);
            scheduledTasks.remove(taskId);
        }
    }
}