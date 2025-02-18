package com.techpuram.saascore.scheduler;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SchedulerRepo extends JpaRepository<Scheduler, Integer> {
    List<Scheduler> findByStatusName(String statusName);
}