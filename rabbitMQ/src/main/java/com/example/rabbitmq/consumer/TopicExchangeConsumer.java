package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.TopicExchangeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 23:30
 */
@Component
public class TopicExchangeConsumer {

    @RabbitListener(queues = TopicExchangeMessage.QUEUE_1)
    public void topicMessageConsumer1(TopicExchangeMessage message){
        System.out.println("Topic consumer-1, 消息:" + message + ",线程：" + Thread.currentThread().getId());
    }

    @RabbitListener(queues = TopicExchangeMessage.QUEUE_2)
    public void topicMessageConsumer2(TopicExchangeMessage message){
        System.out.println("Topic consumer-2, 消息:" + message + ",线程：" + Thread.currentThread().getId());
    }
}
