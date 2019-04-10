package com.gxn.diamond.provider.config;

import com.gxn.diamond.tools.wapper.listener.RateLimitListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/10
 */
@Configuration
@Slf4j
@ConditionalOnClass(value = RateLimitListener.class)
public class RateLimitConfiguration {

    @Bean
    public RateLimitListener create(){
        return new RateLimitListener();
    }

}
