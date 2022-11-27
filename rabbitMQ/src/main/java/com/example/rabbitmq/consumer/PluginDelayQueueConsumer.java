package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.PluginDelayMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/27
 * @time: 16:35
 */
@Component
public class PluginDelayQueueConsumer {

    @RabbitListener(queues = PluginDelayMessage.QUEUE)
    public void handler(Message<PluginDelayMessage> message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("收到延迟消息：" + message);
        channel.basicAck(deliveryTag, false);
    }
}
