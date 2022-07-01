package com.yzm.designmode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DesignmodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesignmodeApplication.class, args);
    }

}
