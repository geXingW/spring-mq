package com.example.rabbitmq.processor;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 21:53
 */
public class ExpireMessagePostProcessor implements MessagePostProcessor {
    /**
     * 延迟时间，毫秒
     */
    private final Long ttl;

    public ExpireMessagePostProcessor(long ttl) {
        this.ttl = ttl;
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setExpiration(ttl.toString());
        return message;
    }
}
