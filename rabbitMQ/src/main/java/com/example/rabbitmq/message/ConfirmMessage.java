package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/2
 * @time: 12:20
 */
@Data
@NoArgsConstructor
public class ConfirmMessage implements Serializable {

    private static final long serialVersionUID = -6270148760480408264L;

    public static final String QUEUE = "confirm-queue";

//    public static final String EXCHANGE = "confirm-exchange";

    public int id;

    public String title;

    public ConfirmMessage(int id) {
        this.id = id;
        this.title = "Confirm message " + id;
    }
}
