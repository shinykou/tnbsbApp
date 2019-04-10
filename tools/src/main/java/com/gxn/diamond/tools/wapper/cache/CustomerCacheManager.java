package com.gxn.diamond.tools.wapper.cache;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NonNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/10
 */
@Data
public  class CustomerCacheManager<C> implements CacheManager {
    private String prefix = "";
    private C nativeCache;
    private ConcurrentMap<String, AbstractCache> caches = Maps.newConcurrentMap();

    public CustomerCacheManager(C nativeCache) {
        this.nativeCache= nativeCache;
    }

    public Cache getCache(@NonNull String name) {
        CustomerCache cache = (CustomerCache)this.caches.get(name);
        if(null == cache) {
            AbstractCache instance = new CustomerCache();
            instance.setNativeCache(nativeCache);
            int expireTime= Integer.parseInt(name);
            instance.setExpireTime(expireTime);
            instance.setPrefix(this.prefix);
            this.caches.putIfAbsent(name, instance);
            cache = (CustomerCache) instance;
        }
        return cache;
    }

    public Collection<String> getCacheNames() {
        return this.caches.keySet();
    }

}
