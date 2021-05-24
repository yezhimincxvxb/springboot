package com.yzm.druid.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DruidConfig {

    // 主要实现web监控的配置处理
    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
        //表示进行druid监控的配置处理操作
        ServletRegistrationBean<StatViewServlet> servlet = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        servlet.addInitParameter("allow", "127.0.0.1,129.168.1.11");//白名单
        servlet.addInitParameter("deny", "129.168.1.12");//黑名单
        servlet.addInitParameter("loginUsername", "root");//用户名
        servlet.addInitParameter("loginPassword", "root");//密码
        servlet.addInitParameter("resetEnable", "false");//允许清空统计数据
        return servlet;
    }

    //监控
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new WebStatFilter());
        filter.addUrlPatterns("/*");//所有请求进行监控处理
        filter.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");//排除
//        filter.addInitParameter("sessionStatEnable", "true");//session统计功能
//        filter.addInitParameter("sessionStatMaxCount", "1000");//缺省sessionStatMaxCount是1000个。你可以按需要进行配置
//        filter.addInitParameter("profileEnable", "true");//druid 0.2.7版本开始支持profile，配置profileEnable能够监控单个url调用的sql列表。
        return filter;
    }

    /**
     * 配置多数据源
     * spring.datasource.druid.one
     * spring.datasource.druid.two
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }
}
