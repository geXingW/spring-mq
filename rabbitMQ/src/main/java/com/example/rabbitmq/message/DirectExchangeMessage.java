package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 17:49
 */
@Data
@NoArgsConstructor
public class DirectExchangeMessage implements Serializable {

    private static final long serialVersionUID = -6270148760480408264L;

    public static final String QUEUE_1 = "direct-exchange-queue-1";

    public static final String QUEUE_2 = "direct-exchange-queue-2";

    public static final String EXCHANGE = "direct-exchange-1";

    public static final String ROUTING_KEY = "direct-exchange-routing-key-1";

    private int index;

    private String title;

    public DirectExchangeMessage(int index) {
        this.index = index;

        this.title = "Direct exchange message-" + index;
    }
}
