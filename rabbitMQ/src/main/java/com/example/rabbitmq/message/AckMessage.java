package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 15:03
 */
@Data
@NoArgsConstructor
public class AckMessage implements Serializable {

    private static final long serialVersionUID = -6270148760480408264L;

    public static final String QUEUE = "ack-queue";

    private int id;

    private String title;

    public AckMessage(int id) {
        this.id = id;

        this.title = "Ack message " + id;
    }
}
