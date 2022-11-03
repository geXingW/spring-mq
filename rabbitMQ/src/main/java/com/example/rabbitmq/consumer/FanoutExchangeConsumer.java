package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.FanoutExchangeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 23:51
 */
@Component
public class FanoutExchangeConsumer {
    @RabbitListener(queues = FanoutExchangeMessage.QUEUE_1)
    public void fanoutExchangeConsumer1(FanoutExchangeMessage message){
        System.out.println("Fanout consumer-1, 消息:" + message + ",线程：" + Thread.currentThread().getId());
    }

    @RabbitListener(queues = FanoutExchangeMessage.QUEUE_2)
    public void fanoutExchangeConsumer2(FanoutExchangeMessage message){
        System.out.println("Fanout consumer-2, 消息:" + message + ",线程：" + Thread.currentThread().getId());
    }
}
