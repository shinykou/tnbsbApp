package com.gxn.diamond.provider.config;

import com.gxn.diamond.tools.wapper.cache.CustomerCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/10
 */
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.afterPropertiesSet();
        System.out.println("create redisTemplate success");
        return template;
    }

    @Bean
    public CacheManager cacheManager(@Autowired RedisTemplate redisTemplate){
        System.out.println("create CustomerCacheManager success");
        return new CustomerCacheManager(redisTemplate);
    }
}
