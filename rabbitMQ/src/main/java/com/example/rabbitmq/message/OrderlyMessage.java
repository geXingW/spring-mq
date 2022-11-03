package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 22:10
 */
@Data
@NoArgsConstructor
public class OrderlyMessage implements Serializable {

    private static final String ROUTING_KEY_PREFIX = "orderly-routing-key-";

    public static final String QUEUE_1 = "orderly-queue-1";
    public static final String ROUTING_KEY_1 = ROUTING_KEY_PREFIX + "1";

    public static final String QUEUE_2 = "orderly-queue-2";
    public static final String ROUTING_KEY_2 = ROUTING_KEY_PREFIX + "2";

    public static final String QUEUE_3 = "orderly-queue-3";
    public static final String ROUTING_KEY_3 = ROUTING_KEY_PREFIX + "3";

    public static final String QUEUE_4 = "orderly-queue-4";
    public static final String ROUTING_KEY_4 = ROUTING_KEY_PREFIX + "4";

    public static final String EXCHANGE = "orderly-exchange";

    private int id;

    public OrderlyMessage(int id) {
        this.id = id;
    }

    public static String getRoutingKeyById(int id) {
        return ROUTING_KEY_PREFIX + (id % 4 + 1);
    }
}
