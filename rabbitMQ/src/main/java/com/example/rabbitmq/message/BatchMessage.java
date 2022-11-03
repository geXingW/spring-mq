package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 20:05
 */
@Data
@NoArgsConstructor
public class BatchMessage implements Serializable {

    public static final String QUEUE = "batch-queue";

    public static final String EXCHANGE = "batch-exchange";

    public static final String ROUTING_KEY = "batch-routing-key";

    private int id;

    private String title;

    public BatchMessage(int id) {

        this.id = id;

        this.title = "Batch message " + id;
    }
}
