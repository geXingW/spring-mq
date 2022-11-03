package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.DelayMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 21:56
 */
@Component
public class DelayDeadConsumer {

    @RabbitListener(queues = DelayMessage.DEAD_QUEUE)
    public void handleMessage(DelayMessage message) {
        System.out.println("Delay dead message " + message + ", 消费时间：" + (System.currentTimeMillis() / 1000));
    }
}
