package com.gxn.diamond.provider.config;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.ServletRequest;

/**
 * @author gaoxiaoning
 * @version ${version}
 * @createdDate 2019/4/10
 */
@Configuration
@ConditionalOnClass(ServletRequest.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class JettyConfig {

    private static final Logger logger = LoggerFactory.getLogger(JettyConfig.class);

    @Bean
    @ConditionalOnMissingBean({JettyServletWebServerFactory.class})
    public JettyServletWebServerFactory servletContainerFactory(
            @Value("${server.port}") int port,
            @Value("${server.displayName}") String displayName,
            @Value("${server.context-path}") String content,
            @Value("${jetty.threadPool.maxThreads}") final String maxThreads,
            @Value("${jetty.threadPool.minThreads}") final String minThreads,
            @Value("${jetty.threadPool.idleTimeout}") final String idleTimeout) {

        JettyServletWebServerFactory jettyEmbeddedServletContainerFactory = new JettyServletWebServerFactory();
        jettyEmbeddedServletContainerFactory.addServerCustomizers(server -> {
            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(Integer.valueOf(maxThreads));
            threadPool.setMinThreads(Integer.valueOf(minThreads));
            threadPool.setIdleTimeout(Integer.valueOf(idleTimeout));
            server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize",-1);
        });
        jettyEmbeddedServletContainerFactory.setPort(port);
        jettyEmbeddedServletContainerFactory.setContextPath(content);
        jettyEmbeddedServletContainerFactory.setDisplayName(displayName);
        return jettyEmbeddedServletContainerFactory;
    }

}
