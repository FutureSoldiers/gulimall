package com.atguigu.gulimall.ware.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FutureSoldiers
 * @create 2021-03-24 18:28
 */
@Configuration
public class MyRabbitConfig {


    /**
     * 使用JSON 序列化机制,进行消息转换
     * 配置 将消息发送 转为json
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

//    @RabbitListener(queues = "stock.release.stock.queue")
//    public void handle(Message message){
//
//    }

    @Bean
    public Exchange stockEvenExchange(){
        // String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
      return new TopicExchange("stock-event-exchange",true,false);
    }

    @Bean
    public Queue queue(){
        // String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        return new Queue("stock.release.stock.queue",true,false,false);
    }

    @Bean
    public Queue stockDelayQueue(){

        Map<String,Object> arguments = new HashMap<>();

        arguments.put("x-dead-letter-exchange","stock-event-exchange");
        arguments.put("x-dead-letter-routing-key","stock.release");
        arguments.put("x-meessage-ttl",120000);
        /**
         *
         arguments.put("x-dead-letter-exchange","order-event-exchange");
         arguments.put("x-dead-letter-routing-key","order.release.order");
         arguments.put("x-meessage-ttl",60000);
         */

        return new Queue("stock.delay.queue",true,false,false,arguments);
    }

    @Bean
    public Binding stockReleaseBinding(){

        // String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
       return new Binding("stock.release.stock.queue",
               Binding.DestinationType.QUEUE,
               "stock-event-exchange",
               "stock.release.#",
               null);
    }

    @Bean
    public Binding stockLockedBinding(){

        // String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
        return new Binding("stock.delay.queue",
                Binding.DestinationType.QUEUE,
                "stock-event-exchange",
                "stock.locked",
                null);
    }
}
