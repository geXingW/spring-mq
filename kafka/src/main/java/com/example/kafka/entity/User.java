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
 * @time: 18:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private int id;

    private String name;

    private int age;

    public User(int id) {
        this.id = id;
        this.name = "user - " + id;
        this.age = 10 + id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
