package com.example.rabbitmq.controller;

import com.example.rabbitmq.message.*;
import com.example.rabbitmq.processor.ExpireMessagePostProcessor;
import com.rabbitmq.client.ConfirmCallback;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 11:37
 */
@RestController
@RequestMapping("send")
public class SendController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private BatchingRabbitTemplate batchingRabbitTemplate;

    @RequestMapping("user")
    public Boolean sendUser() {
        rabbitTemplate.convertAndSend(UserMessage.QUEUE, new UserMessage(1));

        return true;
    }

    @RequestMapping("direct")
    public Boolean sendDirect() {
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(DirectExchangeMessage.EXCHANGE, DirectExchangeMessage.ROUTING_KEY, new DirectExchangeMessage(i));
        }

        return true;
    }

    @RequestMapping("topic")
    public Boolean sendTopic() {
        for (int i = 0; i < 10; i++) {
            // 符合Routing Key 1，由队列1正常消费的消息
            rabbitTemplate.convertAndSend(TopicExchangeMessage.EXCHANGE, TopicExchangeMessage.ROUTE_KEY_1_PREFIX + i, new TopicExchangeMessage(i));

            // 符合Routing Key，由队列2正常消费的消息
            rabbitTemplate.convertAndSend(TopicExchangeMessage.EXCHANGE, TopicExchangeMessage.ROUTE_KEY_2_PREFIX + i, new TopicExchangeMessage(i));

            // 不符合Routing Key，将被丢弃的消息
            rabbitTemplate.convertAndSend(TopicExchangeMessage.EXCHANGE, TopicExchangeMessage.ROUTE_KEY_IGNORED_PREFIX + i, new TopicExchangeMessage(i));
        }

        return true;
    }

    @RequestMapping("fanout")
    public Boolean sendFanout() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(FanoutExchangeMessage.EXCHANGE, null, new FanoutExchangeMessage(i));
        }

        return true;
    }

    @RequestMapping("confirm/correlated")
    public Boolean sendAckCorrelated() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("correlactionData:" + correlationData + ", ack:" + ack + ", cause:" + cause);
            if (ack) {
                System.out.println("发送成功！");
            } else {
                System.out.println("发送失败！");
            }
        });

        rabbitTemplate.convertAndSend(ConfirmMessage.QUEUE, new ConfirmMessage(1));

        return true;
    }

    @RequestMapping("confirm/simple")
    public Boolean sendAckSimple() {
        rabbitTemplate.invoke(new RabbitOperations.OperationsCallback<Object>() {
            @Override
            public Object doInRabbit(RabbitOperations rabbitOperations) {
                rabbitOperations.convertAndSend(ConfirmMessage.QUEUE, new ConfirmMessage(1));
                rabbitOperations.waitForConfirms(1000);

                return null;
            }
        }, new ConfirmCallback() {
            // 已确认
            @Override
            public void handle(long l, boolean b) throws IOException {
                System.out.println("发送成功，l:" + 1 + ", b" + b);
            }
        }, new ConfirmCallback() {
            // 未确认
            @Override
            public void handle(long l, boolean b) throws IOException {
                System.out.println("发送失败，l:" + 1 + ", b" + b);
            }
        });

        return true;
    }

    @RequestMapping("ack")
    public Boolean ack() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(AckMessage.QUEUE, new AckMessage(i));
        }

        return true;
    }

    @RequestMapping("retry")
    public Boolean retry() {
        rabbitTemplate.convertAndSend(RetryMessage.EXCHANGE, RetryMessage.ROUTING_KEY, new RetryMessage(1));

        return true;
    }

//    @RequestMapping("batch")
//    public Boolean batch() {
//        for (int i = 0; i < 1000; i++) {
//            batchingRabbitTemplate.convertAndSend(BatchMessage.EXCHANGE, BatchMessage.ROUTING_KEY, new BatchMessage(i));
//        }
//
//        return true;
//    }

    @RequestMapping("delay")
    public Boolean delay() {
        ExpireMessagePostProcessor postProcessor = new ExpireMessagePostProcessor(10000L);
        rabbitTemplate.convertAndSend(DelayMessage.EXCHANGE, DelayMessage.ROUTING_KEY, new DelayMessage(1), postProcessor);

        return true;
    }

    @RequestMapping("orderly")
    public Boolean orderly() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(OrderlyMessage.EXCHANGE, OrderlyMessage.getRoutingKeyById(i), new OrderlyMessage(i));
        }

        return true;
    }

    @RequestMapping("return-msg")
    public Boolean returnMsg() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend(DirectExchangeMessage.EXCHANGE, "not-exists-routing-key", new ReturnMessage(1));
        }

        return true;
    }

    @GetMapping("plg-delay-msg")
    public Boolean pluginDelayMsg(){
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            rabbitTemplate.convertAndSend(PluginDelayMessage.EXCHANGE, PluginDelayMessage.ROUTING_KEY, new PluginDelayMessage(i), processor -> {
                processor.getMessageProperties().setDelay(1000 * finalI);
                return processor;
            });
        }
        return true;
    }
}
