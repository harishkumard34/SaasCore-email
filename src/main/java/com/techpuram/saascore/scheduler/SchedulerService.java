package com.techpuram.saascore.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private SchedulerRepo schedulerRepo;

    @Autowired
    private DynamicScheduler dynamicScheduler;

    



    // Fetch all scheduled tasks
    public List<Scheduler> getAllScheduledTasks() {
        log.info("Fetching all scheduled tasks");
        return schedulerRepo.findAll();
    }

    // Fetch a specific scheduled task by ID
    public Scheduler getTaskById(int taskId) {
        log.info("Fetching task with ID {}", taskId);
        return schedulerRepo.findById(taskId).orElse(null);
    }

    // Create a new scheduled task
    public Scheduler createTask(Scheduler task) {
        task.setCreatedTime(System.currentTimeMillis());
        task.setStatusName("pending");

        log.info("Creating new task with name {}", task.getTaskName());
        return schedulerRepo.save(task);
    }

    // Delete a scheduled task by ID
    public void deleteTask(int taskId) {
        if (schedulerRepo.existsById(taskId)) {
            dynamicScheduler.cancelTask(taskId); // Cancel the scheduled task
            schedulerRepo.deleteById(taskId);
            log.info("Deleted task with ID {}", taskId);
        } else {
            log.warn("Task with ID {} not found for deletion", taskId);
        }
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    public void scheduleTasks() {
        List<Scheduler> pendingTasks = schedulerRepo.findByStatusName("pending");
        for (Scheduler task : pendingTasks) {
            dynamicScheduler.executeTask(task);
            task.setStatusName("queue");
            schedulerRepo.save(task);
        }
    }

}