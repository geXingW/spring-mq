package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.RetryMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 15:26
 */
@Component
public class RetryConsumer {

//    @RabbitListener(queues = RetryMessage.QUEUE)
//    public void onMessage(RetryMessage message) {
//        System.out.println("Exception retry queue consumer, 消息:" + message + ",线程：" + Thread.currentThread().getId());
//        // <X> 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
//        throw new RuntimeException("我就是故意抛出一个异常");
//    }

    @RabbitListener(queues = RetryMessage.QUEUE)
    public void onMessage(RetryMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            System.out.println("Nack retry queue consumer, 消息:" + message + ",线程：" + Thread.currentThread().getId());
            //参数为：消息的DeliveryTag，是否批量拒绝，是否重新入队
            channel.basicNack(deliveryTag, false, false);
        } catch (IOException e) {
            System.out.println("消息拒绝签收失败" + e);
        }
    }
}
