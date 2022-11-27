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
public class ReturnMessage implements Serializable {

    private int id;

    private String title;

    public ReturnMessage(int id) {

        this.id = id;

        this.title = "Return message " + id;
    }
}
