package com.gradproject.rawdata.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
    private List<DeviceInfo> deviceInfos = new ArrayList<>();
    private ObjectMapper mapper = JsonMapper.builder().build();
    public ConsumeKafka(MongoRepo repo){
        this.repo = repo;
    }

    @KafkaListener(topics = "raw", groupId = "raw-data-service-234")
    public void ingestMessage(String message){
        count++;
        log.trace(count.toString() +  " messages received in rawData service {}", message);

        try {
            DeviceInfo di = mapper.readValue(message, DeviceInfo.class);
            deviceInfos.add(di);
        } catch (JsonProcessingException e) {
            log.trace(e.getMessage());
            return;
        }
        if(deviceInfos.size() > 100){
            log.trace("saving the devices now as size limit reached");
            repo.saveAll(deviceInfos);
            deviceInfos.clear();
        }
    }
}
