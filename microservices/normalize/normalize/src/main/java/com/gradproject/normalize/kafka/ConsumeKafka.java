package com.gradproject.normalize.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumeKafka {
    private Integer count = 0;
    public ConsumeKafka(){

    }

    @KafkaListener(topics = "normal", groupId = "normal-service")
    public void ingestMessage(String message){
        count++;
        log.trace( count.toString()+ " messages received in normal service {}", message);
        //TODO normalize it and store in postgres
    }
}
