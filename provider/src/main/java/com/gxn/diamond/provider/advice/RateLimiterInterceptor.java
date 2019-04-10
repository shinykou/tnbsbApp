package com.gxn.diamond.provider.advice;

import com.google.common.util.concurrent.RateLimiter;
import com.gxn.diamond.common.exception.OutOfRateLimitException;
import com.gxn.diamond.common.helper.RateLimitUtils;
import com.gxn.diamond.domain.enums.RateLimiterAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * Created by gaoxiaoning on 18/3/1.
 */
@Aspect
@Component
public class RateLimiterInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterInterceptor.class);

    @Around("com.gxn.diamond.provider.advice.CommonPointCut.allCrawlAnalyzeApi() && @annotation(rateLimiterAnnotation)")
    public Object rateCrawlApiLimiter(ProceedingJoinPoint proceedingJoinPoint, RateLimiterAnnotation rateLimiterAnnotation) throws Throwable {
        try {
            tryGetAccess(rateLimiterAnnotation);
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            throw e;
        }
    }

    @Around("com.gxn.diamond.provider.advice.CommonPointCut.allServiceApi() && @annotation(rateLimiterAnnotation)")
    public Object rateServiceApiLimiter(ProceedingJoinPoint proceedingJoinPoint, RateLimiterAnnotation rateLimiterAnnotation) throws Throwable {
        try {
            tryGetAccess(rateLimiterAnnotation);
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            throw e;
        }
    }

    private void tryGetAccess(RateLimiterAnnotation rateLimiterAnnotation) throws OutOfRateLimitException {
        try {
            if (null == rateLimiterAnnotation) {
                return;
            }
            final String rateLimiterName = rateLimiterAnnotation.key();
            final double defaultRate = rateLimiterAnnotation.rate();
            RateLimiter rateLimiter = RateLimitUtils.getRateLimiter(rateLimiterName, defaultRate);
            if ( null == rateLimiter ){
                return;
            }
            boolean isAcquire = rateLimiter.tryAcquire();
            if (isAcquire) {
                return;
            }
            if (!rateLimiterAnnotation.isWait()) {
                logger.error("service busy now ,cant wait throw Exception");
                throw new OutOfRateLimitException();
            } else {
                rateLimiter.acquire();
                return;
            }
        }catch (Exception e){
            throw e;
        }

    }
}
