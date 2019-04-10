package com.gxn.diamond.common.helper;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.RateLimiter;
import com.gxn.diamond.tools.wapper.listener.RateLimitListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/2/20
 */
@Slf4j
@Component
public class RateLimitUtils {

    private static final double DEFALUT_RATE = 1;

    private static Set<String> registKeySet = Sets.newHashSet();

    public static Set<String> getRegistKeySet() {
        return registKeySet;
    }

    private static Map<String, RateLimiter> rateLimiterMap = Maps.newHashMap();
    private static ReentrantLock reentrantLock = new ReentrantLock();


    public RateLimiter getRateLimiter(String key) {
        return getRateLimiter(key, DEFALUT_RATE);
    }

    public static RateLimiter getRateLimiter(String key, double defaultRate) {
        RateLimiter limiter = rateLimiterMap.get(key);
        if (limiter != null) {
            return limiter;
        }
        Lock lock = reentrantLock;
        lock.lock();
        try{
            RateLimiter rateLimiter = rateLimiterMap.get(key);
            if ( null != rateLimiter ) {
                return rateLimiter;
            }
            if (!registKeySet.contains(key)) {
                registKeySet.add(key);
            }
            limiter = RateLimiter.create(defaultRate);
            rateLimiterMap.put(key, limiter);
        }finally {
            lock.unlock();
        }
        return rateLimiterMap.get(key);
    }

    public static void changeRate(String key,float rate) {
        try{
            RateLimiter rateLimiter = rateLimiterMap.get(key);
            if ( rateLimiter == null ){
                rateLimiterMap.put(key,RateLimiter.create(rate));
            }else{
                rateLimiter.setRate(rate);
            }
        }catch (Exception e){
            log.error("changeRate",e);
        }
    }


}
