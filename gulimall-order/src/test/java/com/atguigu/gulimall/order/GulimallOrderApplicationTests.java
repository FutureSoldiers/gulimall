package com.atguigu.gulimall.order;

import com.atguigu.gulimall.order.entity.OrderReturnApplyEntity;
import com.atguigu.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@Slf4j
@SpringBootTest
class GulimallOrderApplicationTests {


    @Autowired
    private AmqpAdmin amqpAdmin;  //创建 exchange 和queue 以及他两的binding

    @Autowired
    private RabbitTemplate rabbitTemplate;  //发送以及接受消息

    @Test
    public void sendMessageTest(){
        OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
        reasonEntity.setId(1L);
        reasonEntity.setCreateTime(new Date());
        reasonEntity.setName("哈哈");
        //1、发送消息 如果发送的消息是个对象,我们会使用序列化机制,将对象写出去 对象必须实现 implements Serializable
        String msg = "hello Word";
        //2、发送的消息是json
        rabbitTemplate.convertAndSend("hello-java-exchange","hello.java",reasonEntity,new CorrelationData(UUID.randomUUID().toString()));
        log.info("消息发送完成{}",reasonEntity);
    }

    /**
     * 1、如何创建Exchange[hello-java-exchange]、Queue、Binding
     *    1)、使用AmqpAdmin进行创建
     * 2、如何收发消息
     */
    @Test
    public void createExchange() {
        DirectExchange directExchange = new DirectExchange("hello-java-exchange",true,false);

        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]创建成功","hello-java-exchange");

    }

    @Test
    public void createQueue(){
        // public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments) {
        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);

        log.info("Queue[{}]创建成功","hello-java-queue");
    }

    @Test
    public void createBinding(){
        //public Binding(String destination 目的地, Binding.DestinationType destinationType 目的地类型, String exchange 交换机, String routingKey 路由键, @Nullable Map<String, Object> arguments 自定义参数) {
        //将exchange指定的交换机和destination目的地进行绑定,是一样rootingKey作为指定的路由键
        Binding binding = new Binding("hello-java-queue",Binding.DestinationType.QUEUE,"hello-java-exchange","hello.java",null);
        amqpAdmin.declareBinding(binding);
        log.info("Binding[{}]创建成功","hello-java-binding");


    }

}
