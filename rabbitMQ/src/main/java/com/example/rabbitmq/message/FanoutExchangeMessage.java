package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 23:45
 */
@Data
@NoArgsConstructor
public class FanoutExchangeMessage implements Serializable {

    private static final long serialVersionUID = -6270148760480408264L;

    public static final String QUEUE_1 = "fanout-exchange-queue-1";
    public static final String QUEUE_2 = "fanout-exchange-queue-2";

    public static final String EXCHANGE = "fanout-exchange-1";

    private int id;

    private String title;

    public FanoutExchangeMessage(int id) {
        this.id = id;
        this.title = "Fanout exchange message-" + id;
    }
}
