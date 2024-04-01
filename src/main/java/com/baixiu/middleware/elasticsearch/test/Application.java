package com.baixiu.middleware.elasticsearch.test;


import com.baixiu.middleware.elasticsearch.transport.ElasticSearchTemplateClient;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.PropertySource;

/**
 * test application
 * @author baixiu
 * @date 2024年04月01日
 */
@PropertySource(value = {
        "classpath:spring.properties",
}, encoding = "utf-8")
@SpringBootApplication(scanBasePackages={"com.baixiu.middleware.elasticsearch"})
public class Application extends SpringBootServletInitializer implements ApplicationContextAware {

    private  ApplicationContext applicationContext;
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        System.out.println ("elasticsearch middleware.test started");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
