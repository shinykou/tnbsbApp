package com.gxn.diamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KafKaService {

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void produce(String message,String topic){
        kafkaTemplate.send(topic,message);
    };



}
