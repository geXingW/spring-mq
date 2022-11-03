package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 10:35
 */
@Data
@NoArgsConstructor
public class UserMessage implements Serializable {

    public static final String QUEUE = "queue-user";

    public static final boolean DURABLE = true;

    public static final boolean EXCLUSIVE =false;

    public static final boolean ATUO_DELETE = false;

    public static final String EXCHANGE = "exchange-user";

    public static final String ROUTING_KEY = "routing-key-1";

    private int id;

    private String name;

    public UserMessage(int id){
        this.id = id;
        this.name = "User-" + id;
    }
}
