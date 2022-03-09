package com.yzm.transactional.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.yzm.transactional.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {
}
