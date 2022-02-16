package com.gradproject.ingest.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumeKafka {
    private final ProduceKafka produceKafka;

    public ConsumeKafka(ProduceKafka produceKafka) {
        this.produceKafka = produceKafka;
    }

    @KafkaListener(topics = "ingest", groupId = "ingest-service")
    public void ingestDataMessage(String data){
        log.trace("data message received {}", data);
        log.trace("sending to normal and raw services");
        produceKafka.sendNormal(data);
        produceKafka.sendRaw(data);
    }
}
