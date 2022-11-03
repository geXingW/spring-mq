package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.DirectExchangeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 18:08
 */
@Component
public class DirectExchangeConsumer {

    @RabbitListener(queues = {DirectExchangeMessage.QUEUE_1})
    public void directExchangeConsumer1(DirectExchangeMessage message){
        System.out.println("Direct consumer-1, 消息:" + message + ",线程：" + Thread.currentThread().getId());
    }

    @RabbitListener(queues = {DirectExchangeMessage.QUEUE_2}, concurrency = "2")
    public void directExchangeConsumer2(DirectExchangeMessage message){
        System.out.println("Direct consumer-2, 消息:" + message + ",线程：" + Thread.currentThread().getId());
    }
}
