package com.example.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootTest
class KafkaApplicationTests {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Test
    void contextLoads() {

    }


    @Test
    public void sendMessage() {
        ListenableFuture send = kafkaTemplate.send("first", "First topic message.");
    }

    @Test
    @KafkaListener(topics = "first", groupId = "consumer-group-first")
    public void listenTopic() {

    }
}
