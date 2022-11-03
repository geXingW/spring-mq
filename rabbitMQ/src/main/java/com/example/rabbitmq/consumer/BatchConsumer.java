package com.example.rabbitmq.consumer;

import com.example.rabbitmq.message.BatchMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 20:28
 */
@Component
public class BatchConsumer {

//    @RabbitListener(queues = BatchMessage.QUEUE, containerFactory = "consumerBatchContainerFactory")
    public void onBatchMessage(List<Message> messages) {
        System.out.println("收到 " + messages.size() + " 条消息");
        System.out.println("第一条消息：" + new String(messages.get(0).getBody()));
//        for (int i = 0; i < messages.size(); i++) {
//            System.out.println("第" + i + "条消息：" + messages.get(i));
//        }

        System.out.println("");
    }
}
