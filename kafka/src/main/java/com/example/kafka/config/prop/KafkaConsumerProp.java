package com.example.kafka.config.prop;

import lombok.Data;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/10/29
 * @time: 16:52
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaConsumerProp {
    /**
     * 指定默认消费者group id
     * */
    private String groupId = "";
    /**
     *    earliest 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
     *    latest  当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
     *    none topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
     */
    private String autoOffsetReset = "earliest";
    /**
     * 是否开启自动提交
     * */
    private Boolean enableAutoCommit = true;
    /**
     * key的反序列化器
     * */
    private String keyDeserializer = StringDeserializer.class.toString();
    /**
     * value的反序列化器
     * */
    private String valueDeserializer = StringDeserializer.class.toString();
    /**
     * 消费者默认等待服务响应时间(毫秒)
     * */
    private String fetchMaxWait;
}
