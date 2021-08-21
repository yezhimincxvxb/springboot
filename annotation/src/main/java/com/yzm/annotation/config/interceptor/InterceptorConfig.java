package com.yzm.annotation.config.interceptor;

import com.yzm.annotation.config.interceptor.inter.UserIdMethodArgumentResolver;
import com.yzm.annotation.config.interceptor.inter.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;

    /**
     * 静态资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 拦截器配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 AdminInterceptor 拦截器
//        registry.addInterceptor(new AdminInterceptor())
//                // 添加拦截路径
//                .addPathPatterns("/**")
//                // 添加不拦截路径
//                .excludePathPatterns(
//                        "/**/*.html", "/**/*.js", "/**/*.css", "/interceptor/login"
//                );
        // 注册 UserInterceptor 拦截器
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/interceptor/login");
    }

    /**
     * 注册请求参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserIdMethodArgumentResolver());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
        configurer.enable("defaultServletName");
    }

}
