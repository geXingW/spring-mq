package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 21:32
 */
@Data
@NoArgsConstructor
public class DelayMessage implements Serializable {
    private static final long serialVersionUID = -6270148760480408264L;

    public static final String QUEUE = "delay-queue";
    public static final String DEAD_QUEUE = "delay-dead-queue";

    public static final String EXCHANGE = "delay-exchange";
    public static final String DEAD_EXCHANGE = "delay-dead-exchange";

    public static final String ROUTING_KEY = "delay-routing-key";
    public static final String DEAD_ROUTING_KEY = "delay-dead-routing-key";

    private int id;

    private long sendTime;

    public DelayMessage(int id) {
        this.id = id;
        this.sendTime = System.currentTimeMillis() / 1000;
    }
}
