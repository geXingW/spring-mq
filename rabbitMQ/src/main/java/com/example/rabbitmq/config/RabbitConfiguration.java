package com.example.rabbitmq.config;

import com.example.rabbitmq.message.BatchMessage;
import com.example.rabbitmq.message.*;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: GeXingW
 * @date: 2022/11/1
 * @time: 10:31
 */
@Configuration
public class RabbitConfiguration {

    /**
     * 默认交换机
     *
     * @return
     */
    @Bean
    public Queue userQueue() {
        /**
         *
         * durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
         * exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
         * autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
         */
        return new Queue(UserMessage.QUEUE, UserMessage.DURABLE, UserMessage.EXCLUSIVE, UserMessage.ATUO_DELETE);
    }

    /**
     * 确认队列
     */
    @Bean
    public Queue confirmQueue() {
        return new Queue(ConfirmMessage.QUEUE, true);
    }

    /**
     * 应答队列
     */
    @Bean
    public Queue ackQueue() {
        return new Queue(AckMessage.QUEUE, true);
    }

    /**
     * 重试队列，并设置对应的私信队列
     */
    @Bean
    public Queue retryQueue() {
        return QueueBuilder.durable(RetryMessage.QUEUE)
                .deadLetterExchange(RetryMessage.EXCHANGE)
                .deadLetterRoutingKey(RetryMessage.DEAD_ROUTING_KEY)
                .build();
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue retryDeadQueue() {
        return new Queue(RetryMessage.DEAD_QUEUE, true);
    }

    /**
     * 重试交换机
     */
    @Bean
    public DirectExchange retryExchange() {
        return new DirectExchange(RetryMessage.EXCHANGE, true, false);
    }

    /**
     * 重试队列绑定交换机
     */
    @Bean
    public Binding retryExchangeBinding() {
        return BindingBuilder.bind(retryQueue()).to(retryExchange()).with(RetryMessage.ROUTING_KEY);
    }

    /**
     * 死信队列绑定交换机
     */
    @Bean
    public Binding deadExchangeBinding() {
        return BindingBuilder.bind(retryDeadQueue()).to(retryExchange()).with(RetryMessage.DEAD_ROUTING_KEY);
    }

    /**
     * 创建直连交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DirectExchangeMessage.EXCHANGE, true, false);
    }

    /**
     * 创建直连交换机队列
     *
     * @return
     */
    @Bean
    public Queue directExchangeQueue1() {
        return new Queue(DirectExchangeMessage.QUEUE_1, true);
    }

    @Bean
    public Queue directExchangeQueue2() {
        return new Queue(DirectExchangeMessage.QUEUE_2, true);
    }

    /**
     * 将队列1绑定到直连交换机
     */
    @Bean
    public Binding directExchangeBinding1() {
        return BindingBuilder.bind(directExchangeQueue1()).to(directExchange()).with(DirectExchangeMessage.ROUTING_KEY);
    }

    /**
     * 将队列2绑定到直连交换机
     */
    @Bean
    public Binding directExchangeBinding2() {
        return BindingBuilder.bind(directExchangeQueue2()).to(directExchange()).with(DirectExchangeMessage.ROUTING_KEY);
    }


    /**
     * 主题交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TopicExchangeMessage.EXCHANGE, true, false);
    }

    /**
     * 主题交换机队列1
     *
     * @return
     */
    @Bean
    public Queue topicExchangeQueue1() {
        return new Queue(TopicExchangeMessage.QUEUE_1, true);
    }

    /**
     * 主题交换机队列2
     *
     * @return
     */
    @Bean
    public Queue topicExchangeQueue2() {
        return new Queue(TopicExchangeMessage.QUEUE_2, true);
    }

    /**
     * 主题交换机队列1绑定主题交换机
     */
    @Bean
    public Binding topicExchangeBinding1() {
        return BindingBuilder.bind(topicExchangeQueue1()).to(topicExchange()).with(TopicExchangeMessage.ROUTE_KEY_PATTERN_1);
    }

    /**
     * 主题交换机队列2绑定主题交换机
     */
    @Bean
    public Binding topicExchangeBinding2() {
        return BindingBuilder.bind(topicExchangeQueue2()).to(topicExchange()).with(TopicExchangeMessage.ROUTE_KEY_PATTERN_2);
    }


    /**
     * 扇出（发布订阅模式）交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FanoutExchangeMessage.EXCHANGE, true, false);
    }

    /**
     * 扇出交换机队列1
     */
    @Bean
    public Queue fanoutExchangeQueue1() {
        return new Queue(FanoutExchangeMessage.QUEUE_1, true);
    }

    /**
     * 扇出交换机队列1
     */
    @Bean
    public Queue fanoutExchangeQueue2() {
        return new Queue(FanoutExchangeMessage.QUEUE_2, true);
    }

    /**
     * 扇出交换机队列1绑定扇出交换机
     */
    @Bean
    public Binding fanoutExchangeBinding1() {
        return BindingBuilder.bind(fanoutExchangeQueue1()).to(fanoutExchange());
    }

    /**
     * 扇出交换机队列1绑定扇出交换机
     */
    @Bean
    public Binding fanoutExchangeBinding2() {
        return BindingBuilder.bind(fanoutExchangeQueue2()).to(fanoutExchange());
    }


    /**
     * 批量发送队列
     */
    @Bean
    public Queue batchQueue() {
        return new Queue(BatchMessage.QUEUE, true);
    }

    /**
     * 批量交换机
     */
    @Bean
    public DirectExchange batchExchange() {
        return new DirectExchange(BatchMessage.EXCHANGE, true, false);
    }

    /**
     *
     */
    @Bean
    public Binding batchExchangeBinding() {
        return BindingBuilder.bind(batchQueue()).to(batchExchange()).with(BatchMessage.ROUTING_KEY);
    }

    // TODO 批量发送与顺序消费冲突
//    /**
//     * 批量发送配置
//     * 所谓批量， 就是spring 将多条message重新组成一条message, 发送到mq。
//     * 从mq接受到这条message后，在重新解析成多条message
//     */
//    @Bean
//    public BatchingRabbitTemplate batchingRabbitTemplate(ConnectionFactory connectionFactory) {
//        // 创建 BatchingStrategy 对象，代表批量策略
//
//        // 一次批量的数量
//        int batchSize = 10;
//
//        // 缓存大小限制,单位字节
//        // simpleBatchingStrategy的策略，是判断message数量是否超过batchSize限制或者message的大小是否超过缓存限制，
//        // 缓存限制，主要用于限制"组装后的一条消息的大小"
//        // 如果主要通过数量来做批量("打包"成一条消息), 缓存设置大点
//        // 详细逻辑请看simpleBatchingStrategy#addToBatch()
//        // 2k
//        int bufferLimit = 2048;
//        // 超过收集的时间的最大等待时长，单位：毫秒
//        int timeout = 10000;
//
//        //注意，该策略只支持一个exchange/routingKey
//        //A simple batching strategy that supports only one exchange/routingKey
//        BatchingStrategy batchingStrategy = new SimpleBatchingStrategy(batchSize, bufferLimit, timeout);
//
//        // 创建 TaskScheduler 对象，用于实现超时发送的定时器
//        TaskScheduler taskScheduler = new ConcurrentTaskScheduler();
//
//        // 创建 BatchingRabbitTemplate 对象
//        return new BatchingRabbitTemplate(connectionFactory, batchingStrategy, taskScheduler);
//    }
//
//    /**
//     * 批量消费配置
//     */
//    @Bean(name = "consumerBatchContainerFactory")
//    public SimpleRabbitListenerContainerFactory consumerBatchContainerFactory(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//
//        //开启批量
//        factory.setBatchListener(true);
//        //设置BatchMessageListener生效
//        factory.setConsumerBatchEnabled(true);
//        //设置监听器一次批量处理的消息数量
//        factory.setBatchSize(10);
//
//        return factory;
//    }


    /**
     * 创建延迟队列
     */
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DelayMessage.QUEUE)
                .deadLetterExchange(DelayMessage.DEAD_EXCHANGE)
                .deadLetterRoutingKey(DelayMessage.DEAD_ROUTING_KEY)
                .build();
    }

    /**
     * 创建延迟交换机
     */
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DelayMessage.EXCHANGE, true, false);
    }

    /**
     * 延迟队列绑定交换机
     */
    @Bean
    public Binding delayExchangeBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DelayMessage.ROUTING_KEY);
    }

    /**
     * 创建延迟队列对应死信队列
     */
    @Bean
    public Queue delayDeadQueue() {
        return new Queue(DelayMessage.DEAD_QUEUE, true);
    }

    /**
     * 创建延迟队列对应死信交换机
     */
    @Bean
    public DirectExchange delayDeadExchange() {
        return new DirectExchange(DelayMessage.DEAD_EXCHANGE, true, false);
    }

    /**
     * 延迟队列 死信交换机绑定死信队列
     */
    @Bean
    public Binding delayDeadExchangeBinding() {
        return BindingBuilder.bind(delayDeadQueue()).to(delayDeadExchange()).with(DelayMessage.DEAD_ROUTING_KEY);
    }


    /**
     * 顺序消息队列1
     */
    @Bean
    public Queue orderlyQueue1(){
        return new Queue(OrderlyMessage.QUEUE_1, true);
    }

    /**
     * 顺序消息队列2
     */
    @Bean
    public Queue orderlyQueue2(){
        return new Queue(OrderlyMessage.QUEUE_2, true);
    }

    /**
     * 顺序消息队列3
     */
    @Bean
    public Queue orderlyQueue3(){
        return new Queue(OrderlyMessage.QUEUE_3, true);
    }

    /**
     * 顺序消息队列1
     */
    @Bean
    public Queue orderlyQueue4(){
        return new Queue(OrderlyMessage.QUEUE_4, true);
    }

    /**
     * 顺序消息交换机
     */
    @Bean
    public DirectExchange orderlyExchange(){
        return new DirectExchange(OrderlyMessage.EXCHANGE, true, false);
    }

    /**
     * 顺序消息队列1 绑定交换机
     */
    @Bean
    public Binding orderlyQueueBinding1(){
        return BindingBuilder.bind(orderlyQueue1()).to(orderlyExchange()).with(OrderlyMessage.ROUTING_KEY_1);
    }

    /**
     * 顺序消息队列2 绑定交换机
     */
    @Bean
    public Binding orderlyQueueBinding2(){
        return BindingBuilder.bind(orderlyQueue2()).to(orderlyExchange()).with(OrderlyMessage.ROUTING_KEY_2);
    }

    /**
     * 顺序消息队列3 绑定交换机
     */
    @Bean
    public Binding orderlyQueueBinding3(){
        return BindingBuilder.bind(orderlyQueue3()).to(orderlyExchange()).with(OrderlyMessage.ROUTING_KEY_3);
    }

    /**
     * 顺序消息队列4 绑定交换机
     */
    @Bean
    public Binding orderlyQueueBinding4(){
        return BindingBuilder.bind(orderlyQueue4()).to(orderlyExchange()).with(OrderlyMessage.ROUTING_KEY_4);
    }


    /**
     * 事务消息
     */
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate){
        // 设置 RabbitTemplate 支持事务
        rabbitTemplate.setChannelTransacted(true);

        // 创建 RabbitTransactionManager 对象
        return new RabbitTransactionManager(connectionFactory);
    }
}
