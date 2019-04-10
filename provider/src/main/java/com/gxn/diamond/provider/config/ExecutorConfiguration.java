package com.gxn.diamond.provider.config;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/10
 */
@Configuration
public class ExecutorConfiguration {

    @Bean("threadPool")
    public ExecutorService createNewPool(){
        return Executors.newFixedThreadPool(10);
    }

    @Bean("listeningThreadPool")
    public ListeningExecutorService createListeningPool(){
        return MoreExecutors.listeningDecorator(createNewPool());
    }

}
