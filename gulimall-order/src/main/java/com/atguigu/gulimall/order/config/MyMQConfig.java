package com.atguigu.gulimall.order.config;

import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.order.entity.OrderEntity;
import com.rabbitmq.client.Channel;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FutureSoldiers
 * @create 2021-03-24 11:39
 */
@Configuration
public class MyMQConfig {



    //@Bean Binding, Queue , Exchange

    /**
     * 容器中的 Binding,Queue,Exchange 都会自动创建(RabbitMQ没有的情况)
     * RabbitMQ 只要有. @Bean 声明的属性发生变化也不会覆盖
     * @return
     */
    @Bean
    public Queue orderDelayQueue(){

        Map<String,Object> arguments = new HashMap<>();

        arguments.put("x-dead-letter-exchange","order-event-exchange");
        arguments.put("x-dead-letter-routing-key","order.release.order");
        arguments.put("x-meessage-ttl",60000);

        //String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        Queue queue = new Queue("order.delay.queue", true, false, false,arguments);

        return queue;
    }

    @Bean
    public Queue orderReleaseOrderQueue(){

        Queue queue = new Queue("order.release.order.queue", true, false, false);
        return queue;

    }

    @Bean
    public Exchange orderEventExchange(){
        //String name, boolean durable, boolean autoDelete
        return new TopicExchange("order-event-exchange", true, false);

    }

    @Bean
    public Binding orderCreateOrderBinding(){

        // String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
    return new Binding("order.delay.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.create.order",
                null);

    }

    @Bean
    public Binding orderReleaseOrderBinding(){
        return new Binding("order.release.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.order",
                null);

    }


    /**
     * 订单释放直接和库存释放进行绑定
     * @return
     */
    @Bean
    public Binding orderReleaseOtherBinding(){
        return new Binding("stock.release.stock.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.other.#",
                null);

    }

    @Bean
    public Queue orderSeckillOrderQueue(){
        return new Queue("order.seckill.order.queue",true,false,false);
    }

    @Bean
    public Binding orderSeckillOrderQueueBingdig(){

      return new Binding("order.seckill.order.queue"
                    ,Binding.DestinationType.QUEUE
                    ,"order-event-exchange"
                    ,"order.seckill.order"
                    ,null);
    }

}
