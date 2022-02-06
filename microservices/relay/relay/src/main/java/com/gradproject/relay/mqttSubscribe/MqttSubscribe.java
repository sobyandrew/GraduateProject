package com.gradproject.relay.mqttSubscribe;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

@Component
@Slf4j
public class MqttSubscribe {
    private final MqttConnection mqtt;

    @Autowired
    public MqttSubscribe(MqttConnection mqtt) {
        this.mqtt = mqtt;
    }

    public void subscribeMqtt(BiConsumer<String, String> callback) throws ExecutionException, InterruptedException {
        log.trace("subscribing to mqtt");
        mqtt.subscribe(payload -> parsePayloadAndRoute(payload, callback));

    }

    private void parsePayloadAndRoute(Mqtt5Publish payload, BiConsumer<String, String> callback){
        log.trace("here");
        final var payloadBytes = payload.getPayloadAsBytes();
        log.trace("parse payload");
        if(payloadBytes.length == 0){
            log.trace("no payload in message");
            return;
        }


        final var topic = payload.getTopic().getLevels().get(0);

        log.trace("topic: " + topic);

        callback.accept(topic, new String(payload.getPayloadAsBytes(), StandardCharsets.UTF_8) );
        return;
    }

    public void routeToKafka(String topic, String payload) {

        log.trace("here, on topic: " + topic);
        log.trace("here payload: " + payload);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void run() throws ExecutionException, InterruptedException {
        log.trace("application ready");
        subscribeMqtt(this::routeToKafka);
    }

}
