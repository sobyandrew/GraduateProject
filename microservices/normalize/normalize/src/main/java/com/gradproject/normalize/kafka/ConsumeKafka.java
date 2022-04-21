package com.gradproject.normalize.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradproject.normalize.entity.DeviceInfo;
import com.gradproject.normalize.normalizeservice.NormalizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ConsumeKafka {
  private Integer count = 0;
  private final List<DeviceInfo> devices; // = new ArrayList<>();
  private final ObjectMapper mapper;
  private final NormalizeService normalService;
  private final ProduceKafka kafka;

  @Autowired
  public ConsumeKafka(NormalizeService normalService, ProduceKafka kafka) {
    this.normalService = normalService;
    this.kafka = kafka;

    devices = new ArrayList<>();
    mapper = new ObjectMapper();
  }

  @KafkaListener(topics = "normal", groupId = "normal-service123")
  public void ingestMessage(String message) {
    count++;
    log.trace( count.toString()+ " messages received in normal service {}", message);
    try {
      DeviceInfo di = mapper.readValue(message, DeviceInfo.class);
      devices.add(di);
      if (Integer.valueOf(di.getTemperature()) > 35 || Integer.valueOf(di.getHumidity()) > 50) {
        StringBuilder sb = new StringBuilder();
        sb.append(
            "{ \"deviceId\": \"" + di.deviceId + "\", \"timestamp\": \"" + di.timestamp + "\"");
        if (Integer.valueOf(di.getTemperature()) > 35) {
          log.trace("found temperature alert {}", di);
          sb.append(",\"temperature\": \"" + di.temperature + "\"");
        }
        if (Integer.valueOf(di.getHumidity()) > 50) {
          log.trace("found humidity alert {}", di);
          sb.append(",\"humidity\": \"" + di.humidity + "\"");
        }
        sb.append("}");
        // send to kafka
        kafka.sendAlarm(sb.toString());
      }
    } catch (JsonProcessingException e) {
      log.trace(e.getMessage());
      return;
    }

    if (devices.size() >= 5000) {
      normalService.storeDevices(devices);
      devices.clear();
    }
  }
}
