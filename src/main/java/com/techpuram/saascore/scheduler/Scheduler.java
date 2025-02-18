package com.techpuram.saascore.scheduler;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scheduler", schema = "public")
public class Scheduler {
    
    @Id
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "task_name")
    private String taskName;   
    
    @Column(name = "class_name")
    private String className;

    @Column(name = "status_name")
    private String statusName; 

    @Column(name = "schedule_time")
    private LocalDateTime scheduleTime;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "loop_type")
    private String loopType; 

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "executed_time")
    private Long executedTime;

    @Column(name = "created_time")
    private Long createdTime;

    @Column(name = "created_by")
    private Long createdBy;   
}



