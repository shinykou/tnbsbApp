package com.gxn.diamond.common.kafka.impl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

//@Component
//public class KafkaConsumerListener {
//
//    @KafkaListener(groupId = "customer1",topicPartitions={@TopicPartition(partitions={"0"},topic="test1")})
//    public void consume1(String message){
//        System.out.println("customer1--消费消息:" + message);
//    }
//
//    @KafkaListener(groupId = "customer2",topicPartitions={@TopicPartition(partitions={"1"},topic="test1")})
//    public void consume2(String message){
//        System.out.println("customer2--消费消息:" + message);
//    }
//}