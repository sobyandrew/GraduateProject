package com.gradproject.rawdata.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumeKafka {
    private Integer count = 0;
    public ConsumeKafka(){

    }

    @KafkaListener(topics = "raw", groupId = "raw-data-service")
    public void ingestMessage(String message){
        count++;
        log.trace(count.toString() +  " messages received in rawData service {}", message);
        //TODO store in mongodb
    }
}
