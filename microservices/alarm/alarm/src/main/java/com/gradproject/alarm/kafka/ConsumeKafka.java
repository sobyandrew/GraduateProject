package com.gradproject.alarm.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradproject.alarm.entity.AlarmEntity;
import com.gradproject.alarm.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ConsumeKafka {

    private final AlarmService as;
    private ObjectMapper mapper;

    @Autowired
    public ConsumeKafka(AlarmService as) {
        this.as = as;
        mapper = new ObjectMapper();
    }

    @KafkaListener(topics = "alarm", groupId = "alarm-service")
    public void ingestMessage(String message){
        try {
            log.trace("alarm received");
            log.trace(message);
            JsonNode node = mapper.readTree(message);
            String deviceId = node.get("deviceId").textValue();
            String timestamp = node.get("timestamp").textValue();
            String humidity = "";
            String temperature = "";
            String severity = "moderate";

            if(node.get("humidity") != null){
                humidity = node.get("humidity").textValue();
            }
            if(node.get("temperature") != null){
                temperature = node.get("temperature").textValue();
            }

            AlarmEntity ae = new AlarmEntity();
            ae.setDeviceId(deviceId);
            ae.setTimestamp(timestamp);
            ae.setHumidity(humidity);
            ae.setTemperature(temperature);
            ae.setSeverity(severity);
            ae.setId(UUID.randomUUID().toString());
            as.handleAlarm(ae);
        } catch (JsonProcessingException e) {
            log.trace(e.getMessage());
        }

    }
}
