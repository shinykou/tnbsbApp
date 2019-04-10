package com.gxn.diamond.tools.wapper.cache;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/1/16
 */
@Data
public abstract class AbstractCache<C,K> implements Cache {
    private String prefix = StringUtils.EMPTY;
    private String name;
    private C nativeCache;
    private int expireTime = 0;


    @Override
    public String getName() {
        return "default";
    }

    @Override
    public C getNativeCache() {
        return this.nativeCache;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper wrapper = this.get(key);
        if (null == wrapper || null == wrapper.get()) {
            return null;
        }
        return (T) wrapper.get();
    }

    protected Object encodeValue(Object value) {
        return value;
    }


    protected ValueWrapper decodeValue(Object value) {
        if (null == value) {
            return null;
        }
        return new SimpleValueWrapper(value);
    }
}
