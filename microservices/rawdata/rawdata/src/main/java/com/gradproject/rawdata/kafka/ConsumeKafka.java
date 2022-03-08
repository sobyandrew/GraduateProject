package com.gradproject.rawdata.kafka;

import com.gradproject.rawdata.entity.DeviceInfo;
import com.gradproject.rawdata.repo.MongoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ConsumeKafka {
    private Integer count = 0;
    private final MongoRepo repo;
    private List<String> messages = new ArrayList<String>();
    private List<DeviceInfo> deviceInfos = new ArrayList<>();
    public ConsumeKafka(MongoRepo repo){
        this.repo = repo;
    }

    @KafkaListener(topics = "raw", groupId = "raw-data-service")
    public void ingestMessage(String message){
        count++;
        log.trace(count.toString() +  " messages received in rawData service {}", message);
        messages.add(message);
        messages.it
        if(deviceInfos.a){
            repo.save
        }
        //TODO store in mongodb
    }
}
