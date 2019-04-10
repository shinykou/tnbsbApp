package com.gxn.diamond.provider.listener;

import com.gxn.diamond.common.helper.MoggerUtil;
import com.gxn.diamond.common.helper.RateLimitUtils;
import com.gxn.diamond.tools.wapper.listener.RateLimitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.management.RuntimeErrorException;

@Configuration
//此注解需要依赖容器中crawlResourceBizImpl 的bean执行完毕后，如果该bean不存在，将会抛出异常：A component required a bean named 'crawlResourceBizImpl' that could not be found.
//@DependsOn("crawlResourceBizImpl")
public class ContentListener {

    @Autowired
    private RateLimitListener rateLimitListener;

    @PostConstruct
    public void listenChangeRate(){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while( true ){
                        RateLimitListener.RateLimitModel rateLimitModel = rateLimitListener.getRateLimitModel();
                        if ( null != rateLimitModel ) RateLimitUtils.changeRate(rateLimitModel.getKey(),rateLimitModel.getRate());
                    }
                }
            }).start();
        } catch (Exception e) {
            throw new RuntimeErrorException(new Error(
                    "listenChangeRate", e));
        }
    }

    @PostConstruct
    public void changeCrawlMatchReg(){
        MoggerUtil.COMMON.info("changeDreamMatchReg.started...");
        MoggerUtil.COMMON.info("changeDreamMatchReg.finished...");
    }


}
