package com.yzm.annotation.config.interceptor;

import com.yzm.annotation.config.interceptor.inter.AdminInterceptor;
import com.yzm.annotation.config.interceptor.inter.UserIdMethodArgumentResolver;
import com.yzm.annotation.config.interceptor.inter.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private UserInterceptor userInterceptor;

    /**
     * 这个方法是用来配置静态资源的，比如html，js，css，等等
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 AdminInterceptor 拦截器
//        registry.addInterceptor(adminInterceptor)
//                // 添加拦截路径
//                .addPathPatterns("/**")
//                // 添加不拦截路径
//                .excludePathPatterns(
//                        "/**/*.html", "/**/*.js", "/**/*.css", "/interceptor/login"
//                );
        // 注册 UserInterceptor 拦截器
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/interceptor/login");
    }

    /**
     * 注册请求参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserIdMethodArgumentResolver());
    }


}
