package com.example.rabbitmq.message;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/27
 * @time: 16:17
 */
@Data
public class PluginDelayMessage implements Serializable {
    private static final long serialVersionUID = -6270148760480408264L;

    public static final String QUEUE = "plugin-delay-queue";
    public static final String DEAD_QUEUE = "plugin-delay-dead-queue";

    public static final String EXCHANGE = "plugin-delay-exchange";
    public static final String DEAD_EXCHANGE = "plugin-delay-dead-exchange";

    public static final String ROUTING_KEY = "plugin-delay-routing-key";
    public static final String DEAD_ROUTING_KEY = "plugin-delay-dead-routing-key";

    private int id;

    private long sendTime;

    public PluginDelayMessage(int id) {
        this.id = id;
        this.sendTime = System.currentTimeMillis() / 1000;
    }
}
