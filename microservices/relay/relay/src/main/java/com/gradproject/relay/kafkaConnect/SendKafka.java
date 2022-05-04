package com.gradproject.relay.kafkaConnect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendKafka {
    private final KafkaTemplate<String, String> kafka;

    public SendKafka(KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    public void sendKafkaMessage(String message){
//        log.trace("sending message to kafka");
        kafka.send("ingest", message);

    }
}
