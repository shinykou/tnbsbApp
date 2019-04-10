package com.gxn.diamond.provider.advice;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by gaoxiaoning on 18/4/3.
 */
@Component
public class CommonPointCut {

    @Pointcut(value = "execution( * com.gxn.diamond.biz..*.*(..) )")
    public void allCrawlAnalyzeApi() {
    }

    @Pointcut(value = "execution( * com.gxn.diamond.service..*.*(..) )")
    public void allServiceApi() {
    }

}
