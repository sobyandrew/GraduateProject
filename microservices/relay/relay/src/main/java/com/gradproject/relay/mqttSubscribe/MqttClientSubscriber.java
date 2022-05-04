package com.gradproject.relay.mqttSubscribe;

import com.gradproject.relay.kafkaConnect.SendKafka;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

@Component
@Slf4j
public class MqttClientSubscriber {
  private final MqttClientConnector mqtt;
  private final SendKafka sendKafka;

  private Integer count =0;
  @Autowired
  public MqttClientSubscriber(MqttClientConnector mqtt, SendKafka sendKafka) {
    this.mqtt = mqtt;
    this.sendKafka = sendKafka;
  }

  public void subscribeMqtt(BiConsumer<String, String> callback)
      throws ExecutionException, InterruptedException {
    log.trace("subscribing to mqtt");
    mqtt.subscribe(payload -> getPayloadAndSend(payload, callback));
  }

  private void getPayloadAndSend(Mqtt5Publish payload, BiConsumer<String, String> callback) {
    final var payloadBytes = payload.getPayloadAsBytes();
    count++;
//    log.trace("parse payload");
    if (payloadBytes.length == 0) {
      log.trace("no payload in message");
      return;
    }

    final var topic = payload.getTopic().getLevels().get(0);

//    log.trace("topic: " + topic);
    log.trace("message count " + count.toString());
    callback.accept(topic, new String(payload.getPayloadAsBytes(), StandardCharsets.UTF_8));
    return;
  }

  public void sendKafka(String topic, String payload) {
//    log.trace("sending to kafka");
    sendKafka.sendKafkaMessage(payload);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void runOnStartup() throws ExecutionException, InterruptedException {
    log.trace("application ready");
    subscribeMqtt(this::sendKafka);
  }
}
