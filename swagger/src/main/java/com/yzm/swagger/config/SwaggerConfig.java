package com.yzm.swagger.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2 // 启动swagger
@EnableSwaggerBootstrapUI
//是否开启swagger，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），可根据springboot的多环境配置进行设置
@ConditionalOnProperty(name = "swagger.enabled",  havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 方法需要有ApiOperation注解才能生存接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //.apis(RequestHandlerSelectors.basePackage("com.yzm.swagger.controller"))
                // 可以根据url路径设置哪些请求加入文档，忽略哪些请求，any所有
                .paths(PathSelectors.any())
                .build()
                // 如何保护我们的Api，有三种验证（ApiKey, BasicAuth, OAuth）
                .securitySchemes(apiKeys())
                //设置全局参数
                .globalOperationParameters(getGlobalParameters());
    }

    private List<Parameter> getGlobalParameters() {
        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name("token")
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build());
        return parameters;
    }

    private List<SecurityScheme> apiKeys() {
        List<SecurityScheme> list = new ArrayList<>(1);
        list.add(new ApiKey("Authorization", "admin", "yzm"));
        return list;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档的标题
                .title("swagger服务")
                // 文档的描述
                .description("swagger服务 API 接口文档")
                // 文档的版本信息-> 1.0.0 Version information
                .version("1.0.0")
                // 联系信息
                .contact(new Contact("跳转网址", "https://www.baidu.com", "联系邮箱"))
                .build();
    }

}
