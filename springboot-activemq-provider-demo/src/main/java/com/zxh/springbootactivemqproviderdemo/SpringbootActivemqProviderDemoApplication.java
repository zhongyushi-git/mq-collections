package com.zxh.springbootactivemqproviderdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SpringbootActivemqProviderDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootActivemqProviderDemoApplication.class, args);
    }

}
