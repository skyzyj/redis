package com.itos.redis_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext cac = SpringApplication.run(RedisDemoApplication.class, args);
        TestRedis testRedis = cac.getBean(TestRedis.class);
        testRedis.getMessage();
    }

}
