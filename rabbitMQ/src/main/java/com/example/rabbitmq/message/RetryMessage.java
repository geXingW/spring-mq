package com.example.rabbitmq.message;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 15:24
 */
@Data
public class RetryMessage implements Serializable {

    private static final long serialVersionUID = -6270148760480408264L;

    public static final String QUEUE = "retry-queue";

    public static final String EXCHANGE = "retry-exchange";

    public static final String ROUTING_KEY = "retry-routing-key";

    public static final String DEAD_QUEUE = "retry-dead-queue";

    public static final String DEAD_ROUTING_KEY = "retry-dead-routing-key";

    private int id;

    private String title;

    public RetryMessage(int id) {
        this.id = id;
        this.title = "Retry message " + id;
    }
}
