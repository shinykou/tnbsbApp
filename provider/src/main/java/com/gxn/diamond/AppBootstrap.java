package com.gxn.diamond;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用启动类
 *
 * @author gaoxiaoning created on  17/12/13.
 * @version $Id$
 */

@SpringBootApplication(exclude = MybatisAutoConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.gxn.diamond.dao")
@EnableScheduling
@PropertySource({
        "classpath:/config/app.properties",
        "classpath:/config/redis.properties"
})
public class AppBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(AppBootstrap.class, args);
    }
    }



