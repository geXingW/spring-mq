package com.example.rabbitmq.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 22:42
 */
@Data
@NoArgsConstructor
public class TopicExchangeMessage implements Serializable {

    private static final long serialVersionUID = -6270148760480408264L;

    public static final String QUEUE_1 = "topic-exchange-queue-1";
    public static final String QUEUE_2 = "topic-exchange-queue-2";

    public static final String EXCHANGE = "topic-exchange-1";

    /**
     * 各单词直减以 . 隔开
     *  # 代表0或多个单词，*代表1或多个单词
     */
    public static final String ROUTE_KEY_PATTERN_1 = "topic-exchange-router-key-1.#";
    public static final String ROUTE_KEY_PATTERN_2 = "topic-exchange-router-key-2.*";

    public static final String ROUTE_KEY_1_PREFIX = "topic-exchange-router-key-1.";
    public static final String ROUTE_KEY_2_PREFIX = "topic-exchange-router-key-2.";
    public static final String ROUTE_KEY_IGNORED_PREFIX = "topic-exchange-router-key.";


    private int id;

    private String title;

    public TopicExchangeMessage(int id) {
        this.id = id;
        this.title = "Topic exchange message-" + id;
    }
}
