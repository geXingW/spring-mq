package com.example.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/10/29
 * @time: 18:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private long id;

    private String title;

    public Order(long id) {
        this.id = id;
        this.title = "Order - " + id;
    }
}
