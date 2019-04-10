package com.gxn.diamond.common.kafka.impl;

import com.gxn.diamond.common.kafka.IKafkaConsumerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerImpl implements IKafkaConsumerListener {
    private Logger logger = LoggerFactory.getLogger(KafkaListenerImpl.class);

    @Override
    public boolean consumer(String message) {
        try{
            logger.info("message:"+message);
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
