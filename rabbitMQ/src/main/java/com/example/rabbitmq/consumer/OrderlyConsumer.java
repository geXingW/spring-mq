package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.OrderlyMessage;
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
 * @date: 2022/11/2
 * @time: 22:12
 */
@Component
public class OrderlyConsumer {

    @RabbitListener(queues = {OrderlyMessage.QUEUE_1, OrderlyMessage.QUEUE_2, OrderlyMessage.QUEUE_3, OrderlyMessage.QUEUE_4})
    public void handleMessage(Message<OrderlyMessage> message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("Orderly message " + message.getPayload().getId() + ", Queue:" + getQueue(message) + ", Thread:"+Thread.currentThread().getId());
        channel.basicAck(deliveryTag, true);
    }

    private static String getQueue(Message<OrderlyMessage> message){
        return message.getHeaders().get("amqp_consumerQueue", String.class);
    }
}
