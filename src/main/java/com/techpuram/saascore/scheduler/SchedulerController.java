package com.techpuram.saascore.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/scheduler")
public class SchedulerController {

    @Autowired
    private final SchedulerService schedulerService;

    @GetMapping("/")
    public ResponseEntity<List<Scheduler>> getAllSchedulers() {
        return ResponseEntity.ok().body(schedulerService.getAllScheduledTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Scheduler> getSchedulerById(@PathVariable("taskId") Integer taskId) {
        return ResponseEntity.ok().body(schedulerService.getTaskById(taskId));
    }

    @PostMapping("/")
    public ResponseEntity<Scheduler> createScheduler(@RequestBody Scheduler scheduler) {
        return ResponseEntity.ok().body(schedulerService.createTask(scheduler));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteScheduler(@PathVariable("taskId") Integer taskId) {
        schedulerService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }
}
