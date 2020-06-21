package com.gxn.diamond.provider.config;

import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JettyConfigV2 {


    @Value("${server.display-name}")
    private String displayName;
    @Value("${server.context-path}")
    private String contentPath;


    @Value("${server.port}")
    private int httpPort;


    @Value("${https.keystore}")
    private String keyStorePath;
    @Value("${https.keystore-password}")
    private String keyStorePassword;
    @Value("${https.port}")
    private int httpsPort;
    @Value("${https.keystore-type}")
    private String keystoreType;


    @Value("${jetty.threadPool.maxThreads}")
    private int maxThreads;
    @Value("${jetty.threadPool.minThreads}")
    private int minThreads;
    @Value("${jetty.threadPool.idleTimeout}")
    private int idleTimeout;


    /**
     * 通过构造工厂造1个jetty
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        JettyServletWebServerFactory jetty = new JettyServletWebServerFactory();
        jetty.setDisplayName(displayName);
        jetty.setContextPath(contentPath);
        customizeJetty(jetty);
        return jetty;
    }

    /**
     * 为jetty服务器开通http端口和https,并配置线程
     */
    private void customizeJetty(JettyServletWebServerFactory container) {
        container.addServerCustomizers((Server server) -> {
            //配置线程
            threadPool(server);

            // 添加HTTP配置
            ServerConnector connector = new ServerConnector(server);
            connector.setPort(httpPort);

            // 添加HTTPS配置
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setKeyStorePath(keyStorePath);
            sslContextFactory.setKeyStorePassword(keyStorePassword);
            sslContextFactory.setKeyStoreType(keystoreType);

            HttpConfiguration config = new HttpConfiguration();
            config.setSecureScheme(HttpScheme.HTTPS.asString());
            config.addCustomizer(new SecureRequestCustomizer());

            ServerConnector sslConnector = new ServerConnector(
                    server,
                    new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                    new HttpConnectionFactory(config));
            sslConnector.setPort(httpsPort);
            server.setConnectors(new Connector[]{connector, sslConnector});
        });
    }

    /**
     * jetty线程配置
     */
    private void threadPool(Server server) {
        // connections
        final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
        //默认最大线程连接数200
        threadPool.setMaxThreads(maxThreads);
        //默认最小线程连接数8
        threadPool.setMinThreads(minThreads);
        //默认线程最大空闲时间60000ms
        threadPool.setIdleTimeout(idleTimeout);
    }

//    /**
//     * 配置文件上传
//     */
//    @Bean
//    MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        //  单个数据大小
//        factory.setMaxFileSize("100MB"); // KB,MB
//        /// 总上传数据大小
//        factory.setMaxRequestSize("200MB");
//        return factory.createMultipartConfig();
//    }

}