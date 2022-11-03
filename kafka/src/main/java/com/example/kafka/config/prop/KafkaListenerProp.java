package com.example.kafka.config.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/10/29
 * @time: 16:54
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.kafka.listener")
public class KafkaListenerProp {
    /**
     * 启用线程数（提高并发）
     * */
    private Integer concurrency;
    /**
     *  手动提交的方式，当enable-auto-commit: false时起作用
     *  manual:手动调用Acknowledgment.acknowledge()后立即提交
     *  record:当每一条记录被消费者监听器（ListenerConsumer）处理之后提交
     *  batch:当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后提交
     *  time: 当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，距离上次提交时间大于TIME时提交
     *  count:当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后，被处理record数量大于等于COUNT时提交
     *  count_time:当每一批poll()的数据被消费者监听器（ListenerConsumer）处理之后, 手动调用Acknowledgment.acknowledge()后提交
     * */
    private String ackMode;
    /**
     * 消费超时时间
     */
    private Long pollTimeout;
    /**
     * 是否开启批量处理
     * */
    private Boolean batchListener;
}
