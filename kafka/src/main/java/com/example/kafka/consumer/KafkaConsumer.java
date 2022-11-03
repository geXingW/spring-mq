package com.example.kafka.consumer;

import com.example.kafka.entity.Order;
import com.example.kafka.entity.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/10/29
 * @time: 11:09
 */
@Component
public class KafkaConsumer {

    @KafkaListener(id = "first-listener-1", topics = "first")
    public void firstListener1(String in) {
        System.out.println("first-listener-1: " + in);
    }

    @KafkaListener(id = "first-listener-2", topics = "first")
    public void firstListener2(String in) {
        System.out.println("first-listener-2: " + in);
    }

    @KafkaListener(id = "second-listener-1", topics = "second")
    public void secondListener(String in) {
        System.out.println("secondListener: " + in);
    }

    @KafkaListener(id = "callback-listener-1", topics = "callback", topicPartitions = {@TopicPartition(topic = "callback", partitions = {"0", "1"})})
    public void callbackListener1(ConsumerRecord<?, ?> record) {
        System.out.println("Listener1 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "callback-listener-2", topics = "callback")
    public void callbackListener2(ConsumerRecord<?, ?> record) {
        System.out.println("Listener2 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "callback-listener-3", topics = "callback")
    public void callbackListener3(ConsumerRecord<?, ?> record) {
        System.out.println("Listener3 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "group-listener-1", topics = "group", groupId = "group-listener")
    public void groupListener1(ConsumerRecord<?, ?> record) {
        System.out.println("Group listener1 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "group-listener-2", topics = "group", groupId = "group-listener")
    public void groupListener2(ConsumerRecord<?, ?> record) {
        System.out.println("Group listener2 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "user-listener-1", topics = "user")
    public void userListener1(ConsumerRecord<?, ?> record) {
        System.out.println("User listener1 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "user-listener-2", topics = "user")
    public void userListener2(ConsumerRecord<?, ?> record) {
        System.out.println("User listener2 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "user-listener-3", topics = "user")
    public void userListener3(ConsumerRecord<?, ?> record) {
        System.out.println("User listener3 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "order-listener-1", topics = "order")
    public void orderListener1(ConsumerRecord<?, ?> record) {
        System.out.println("Order listener1 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

    @KafkaListener(id = "order-listener-2", topics = "order")
    public void orderListener2(ConsumerRecord<?, ?> record) {
        System.out.println("Order listener2 topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
    }

//    @KafkaListener(id="ack-listener", topics = "ack")
//    public void ackListener(ConsumerRecord<?, ?> record){
//        System.out.println("Ack listener topic: " + record.topic() + ",msg: " + record.value()+ ",partition: " + record.partition());
//    }

    @KafkaListener(id = "ack-listener", topics = "ack", concurrency = "2")
    public void ackListener(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment) {
        System.out.println("Ack listener topic: " + record.topic() + ",msg: " + record.value() + ",partition: " + record.partition());
        acknowledgment.acknowledge();
    }

    @KafkaListener(id = "entity-listener", topics = "entity")
    public void entityListener(User user) {
        System.out.println("Entity listener topic: " + user);
    }

    @KafkaListener(id = "concurrent-listener", topics = "concurrent", concurrency = "3")
    public void concurrentListener(Order order, Acknowledgment acknowledgment) {
        System.out.println("Concurrent listener topic: concurrent, Thread:" + Thread.currentThread().getId() + ", order:" + order.getTitle());
        acknowledgment.acknowledge();
    }

}
