package com.xxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement  //开启mybatis的事务支持
@SpringBootApplication
public class KcsjApplication {

    public static void main(String[] args) {
        SpringApplication.run(KcsjApplication.class, args);
    }

}
