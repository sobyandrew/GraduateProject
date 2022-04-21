package com.gradproject.relay.mqttSubscribe;

import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
@Slf4j
public class MqttConnection {

    private final Mqtt5AsyncClient client;
    private final ExecutorService subscribePool;

    public MqttConnection(@Value("${mqtt.broker.address}") String host, @Value("${mqtt.broker.port}") Integer port) {
        this.client = MqttClient.builder().serverHost(host).serverPort(port).useMqttVersion5().buildAsync();
        this.subscribePool = Executors.newCachedThreadPool();
        this.client.connect();
    }

    public void subscribe(final Consumer<Mqtt5Publish> callback) throws ExecutionException, InterruptedException {
        log.trace("subscribing to all messages from broker");
        final var subscribeReq = Mqtt5Subscribe.builder().topicFilter("#").build();

        client.subscribe(subscribeReq, callback, subscribePool).get();
    }
}
