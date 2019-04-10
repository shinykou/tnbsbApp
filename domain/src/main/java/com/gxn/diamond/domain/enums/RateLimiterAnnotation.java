package com.gxn.diamond.domain.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用本注解，会对访问速度进行限制在指定的速度大小
 * 如果 isWait = false ,当请求超限制速度时会抛出OutOfLimitException
 * 如果 isWait = true ,当请求超限制速度时会对当前方法进行堵塞。
 * 异常需要由外层调用方法显式try，catch
 * Created by gaoXiaoNing on 18/3/1.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiterAnnotation {
    String key();

    float rate();

    boolean isWait() default true;
}
