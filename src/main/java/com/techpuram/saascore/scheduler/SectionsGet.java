package com.techpuram.saascore.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techpuram.saascore.db.SectionsRepo;
import com.techpuram.saascore.entity.modules.Sections;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SectionsGet implements SchedulerInterface {

    @Autowired
    private SectionsRepo sectionsRepo;

    @Override
    public void runTask(Scheduler task) {
        List<Sections> sections = sectionsRepo.findAll();
        log.info("Running task in sectionsGet class. Sections: {}", sections);
    }
}