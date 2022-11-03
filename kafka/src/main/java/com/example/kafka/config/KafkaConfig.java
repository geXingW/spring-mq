package com.example.kafka.config;

import com.example.kafka.component.ApplicationPartitioner;
import com.example.kafka.config.prop.KafkaConsumerProp;
import com.example.kafka.config.prop.KafkaListenerProp;
import com.example.kafka.config.prop.KafkaProducerProp;
import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/10/28
 * @time: 18:23
 */
//@Configuration
//@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConfig {

    @Autowired
    KafkaProducerProp kafkaProducerProp;

    @Autowired
    KafkaConsumerProp kafkaConsumerProp;

    @Autowired
    KafkaListenerProp kafkaListenerProp;

    // 配置的自动装载，只支持基本类型
    public static List<String> bootstrapServers;

    public static List<String> getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(List<String> bootstrapServers) {
        KafkaConfig.bootstrapServers = bootstrapServers;
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate(producerFactory());
    }

    @Bean
    public KafkaAdmin.NewTopics orderTopic() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("first").replicas(3).partitions(10).build(),
                TopicBuilder.name("second").replicas(3).partitions(1).build(),
                TopicBuilder.name("callback").replicas(3).partitions(3).build(),
                TopicBuilder.name("group").replicas(3).partitions(3).build(),
                TopicBuilder.name("user").replicas(3).partitions(3).build(),
                TopicBuilder.name("order").replicas(3).partitions(2).build(),
                TopicBuilder.name("ack").replicas(3).partitions(2).build()
        );
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Object, Object>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        // 线程数配置
        factory.setConcurrency(kafkaListenerProp.getConcurrency());

        // 手动提交
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        // 批量处理
//        factory.setBatchListener(kafkaListenerProp.getBatchListener());


        return factory;
    }

    @Bean
    public ConsumerFactory<Object, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory(consumerConfigs());
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new ConcurrentHashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerProp.getAcks());

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerProp.getKeySerializer());

        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerProp.getValueSerializer());

        // 自定义分区器
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.example.kafka.component.ApplicationPartitioner");

        return props;
    }

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new ConcurrentHashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerProp.getGroupId());

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerProp.getAutoOffsetReset());

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerProp.getKeyDeserializer());

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerProp.getValueDeserializer());

        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RoundRobinAssignor");


        return props;
    }
}
