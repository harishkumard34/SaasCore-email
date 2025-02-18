package com.techpuram.saascore.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techpuram.saascore.db.FieldsRepo;
import com.techpuram.saascore.entity.modules.Fields;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FieldsGet implements SchedulerInterface {

    @Autowired
    private FieldsRepo fieldsRepo;

    @Override
    public void runTask(Scheduler task) {
        List<Fields> fields = fieldsRepo.findAll();
        log.info("Running task in FieldsGet class. Fields: {}", fields);
    }
}