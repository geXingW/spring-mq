package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.RetryMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 15:31
 */
@Component
public class DeadQueueConsumer {

    @RabbitListener(queues = RetryMessage.DEAD_QUEUE)
    public void onMessage(RetryMessage message){
        System.out.println("Dead queue consumer, 消息:" + message + ",线程：" + Thread.currentThread().getId());
    }
}
