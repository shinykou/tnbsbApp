package com.gxn.diamond.tools.wapper.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/10
 */
public class CustomerCache<C, K> extends AbstractCache<RedisTemplate<String,Object>,String>{

    @Override
    public ValueWrapper get(Object key) {
        Object result = this.getNativeCache().opsForValue().get(key);
        return decodeValue(result);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        ValueWrapper valueWrapper = get(key);
        return (T) valueWrapper.get();
    }

    @Override
    public void put(Object key, Object value) {
        String finalKey = key.toString();
        Object finalValue = this.encodeValue(value);
        this.getNativeCache().opsForValue().set (finalKey,finalValue,this.getExpireTime(), TimeUnit.SECONDS);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        if ( get(key) == null) put(key,value);
        return decodeValue(value);
    }

    @Override
    public void evict(Object key) {
        this.getNativeCache().delete(key.toString());
    }

    @Override
    public void clear() {
        this.getNativeCache().discard();
    }
}
