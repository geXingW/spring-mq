package com.example.kafka.controller;

import com.example.kafka.entity.Order;
import com.example.kafka.entity.User;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/10/29
 * @time: 11:14
 */
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping("sync")
    public Boolean syncSend() throws ExecutionException, InterruptedException {

        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send("first", "Sync message - " + i).get();
            kafkaTemplate.send("second", "Sync message - " + i).get();
        }
        return true;
    }

    @RequestMapping("async")
    public Boolean asyncSend() {
        for (int i = 0; i < 100; i++) {
            kafkaTemplate.send("first", "Sync message - " + i);
            kafkaTemplate.send("second", "Sync message - " + i);
        }

        return true;
    }

    @RequestMapping("callback")
    public Boolean callback() {
        for (int i = 0; i < 500; i++) {
            ListenableFuture listenableFuture = kafkaTemplate.send("callback", "Callback message - " + i);
            listenableFuture.addCallback(new KafkaSendCallback() {
                @Override
                public void onFailure(KafkaProducerException e) {
                    System.out.println(e);
                }

                @Override
                public void onSuccess(Object result) {
                    SendResult sendResult = (SendResult) result;
                    ProducerRecord producerRecord = sendResult.getProducerRecord();
                    System.out.println("Send result: " + producerRecord.toString());
                }
            });
        }

        return true;
    }

    @RequestMapping("group")
    public Boolean groupSend() {
        for (int i = 0; i < 500; i++) {
            kafkaTemplate.send("group", "Group message - " + i);
        }

        return true;
    }

    @RequestMapping("user")
    public Boolean sendUser() {
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send("user", new User(i).toString());
        }

        return true;
    }

    @RequestMapping("order")
    public Boolean sendOrder() {
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send("order", new User(i).toString());
        }

        return true;
    }

    @RequestMapping("ack")
    public Boolean sendAck() {
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send("ack", "Ack message - " + i);
        }

        return true;
    }

    @RequestMapping("entity")
    public Boolean sendEntity() {
        for (int i = 0; i < 10; i++) {
            kafkaTemplate.send("entity", new User(i));
        }

        return true;
    }

    @RequestMapping("concurrent")
    public Boolean sendConcurrent() {
        for (int i = 0; i < 50; i++) {
            kafkaTemplate.send("concurrent", new Order(i));
        }

        return true;
    }

    @RequestMapping("transaction")
    public Boolean sendTransaction(@RequestParam("rollback") boolean rollback) {
        try {
            kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
                @Override
                public Object doInOperations(KafkaOperations kafkaOperations) {
                    kafkaOperations.send("user", new User(10));
                    kafkaOperations.send("order", new Order(11));
                    if (rollback) {
                        throw new RuntimeException("rollback");
                    }

                    return true;
                }
            });

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
