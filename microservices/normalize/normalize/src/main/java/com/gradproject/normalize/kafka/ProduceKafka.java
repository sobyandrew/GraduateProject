package com.gradproject.normalize.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProduceKafka {

    private final KafkaTemplate<String, String> kafka;

    public ProduceKafka(KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    public void sendAlarm(String data){
        log.trace("send message to alarm service");
        kafka.send("alarm", data);
    }
}
