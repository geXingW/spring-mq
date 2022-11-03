package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.AckMessage;
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
 * @time: 15:11
 */
@Component
public class AckQueueConsumer {

    @RabbitListener(queues = AckMessage.QUEUE)
    public void onMessage(AckMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("Ack consumer-1, 消息:" + message + ",线程：" + Thread.currentThread().getId() + ", Delivery Tag:" + deliveryTag);

        // ack 确认消息
        // 第二个参数 multiple ，用于批量确认消息，为了减少网络流量，手动确认可以被批处。
        // 1. 当 multiple 为 true 时，则可以一次性确认 deliveryTag 小于等于传入值的所有消息
        // 2. 当 multiple 为 false 时，则只确认当前 deliveryTag 对应的消息
        channel.basicAck(deliveryTag, true);

    }
}
