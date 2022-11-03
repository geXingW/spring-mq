package com.example.kafka.config.prop;

import lombok.Data;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/10/29
 * @time: 16:49
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProp {
    /**
     * 批量发送，延迟为30毫秒，如果30ms内凑不够batch则强制发送，提高并发
     * */
    private String lingerMs;
    /**
     * 失败重试时，保证消息顺序性，会降低吞吐量
     */
    private String maxInFlightRequestsPerConnection;
    /**
     *acks = 0 如果设置为零，则生产者将不会等待来自服务器的任何确认，该记录将立即添加到套接字缓冲区并视为已发送。在这种情况下，无法保证服务器已收到记录，并且重试配置将不会生效（因为客户端通常不会知道任何故障），为每条记录返回的偏移量始终设置为-1。
     *acks = 1 这意味着leader会将记录写入其本地日志，但无需等待所有副本服务器的完全确认即可做出回应，在这种情况下，如果leader在确认记录后立即失败，但在将数据复制到所有的副本服务器之前，则记录将会丢失。
     *acks = all 这意味着leader将等待完整的同步副本集以确认记录，这保证了只要至少一个同步副本服务器仍然存活，记录就不会丢失，这是最强有力的保证，这相当于acks = -1的设置。
     */
    private String acks;
    /**
     *压测后给出最适合的值，当前为默认值（批处理字节大小，太大可能oom，或者消息不能及时发送到broker，太小性能不足）
     */
    private String batchSize;
    /**
     * 缓存区大小
     */
    private String bufferMemory;
    /**
     * enable_idempotence开启消息幂等性
     * */
    private Boolean enableIdempotence;
    /**
     *生产者空间不足时阻塞的时间
     */
    private int maxBlockMs;
    /**
     * 消息压缩类型
     */
    private String compressionType;
    /**
     * 消息发送失败重试次数
     * */
    private int retries = 0;
    /**
     * 重试间隔
     * */
    private String retryBackoffMs;
    /**
     * key、value的序列化器
     * */
    private String keySerializer = StringSerializer.class.toString();
    private String valueSerializer = StringSerializer.class.toString();
    /**
     * 消息大小限制
     */
    private String maxRequestSize;
}
