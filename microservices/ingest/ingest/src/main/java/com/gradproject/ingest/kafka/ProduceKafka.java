package com.gradproject.ingest.kafka;

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

    public void sendNormal(String data){
//        log.trace("send message to normalize service");
        kafka.send("normal", data);
    }

    public void sendRaw(String data){
//        log.trace("send message to store raw data service");
        kafka.send("raw", data);
    }
}
