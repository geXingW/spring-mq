package com.example.kafka.component;

import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/10/29
 * @time: 18:09
 */
public class ApplicationPartitioner extends DefaultPartitioner {

    private static final String[] SINGLE_PARTITION_TOPICS = {"order"};

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (Arrays.asList(SINGLE_PARTITION_TOPICS).contains(topic)) {
            return 0;
        }

        return super.partition(topic, key, keyBytes, value, valueBytes, cluster);
    }
}
