package com.gradproject.alarm.service;


import com.gradproject.alarm.entity.AlarmEntity;
import com.gradproject.alarm.repos.MongoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class AlarmService {
    LinkedList<AlarmEntity> alarms = new LinkedList<>();

    private final MongoRepo repo;

    @Autowired
    public AlarmService(MongoRepo repo) {
        this.repo = repo;
    }

    public void handleAlarm(AlarmEntity ae){
        repo.save(ae);
        alarms.addFirst(ae);
        if(alarms.size() > 10){
            alarms.removeLast();
        }
    }

    public List<AlarmEntity> returnAlarms(){
        return alarms.subList(0, alarms.size());
    }
}
