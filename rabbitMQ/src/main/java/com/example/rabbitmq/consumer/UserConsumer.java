package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.UserMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 17:07
 */
@Component
@RabbitListener(queues = UserMessage.QUEUE)
public class UserConsumer {

    @RabbitHandler
    public void onMessage(UserMessage userMessage){
        System.out.println("消息:" + userMessage + ",线程：" + Thread.currentThread().getId());
    }
}
