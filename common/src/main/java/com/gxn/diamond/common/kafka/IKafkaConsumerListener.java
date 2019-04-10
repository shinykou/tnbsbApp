package com.gxn.diamond.common.kafka;

public interface IKafkaConsumerListener {

    boolean consumer(String message);
}
