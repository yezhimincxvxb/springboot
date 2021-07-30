package com.yzm.swagger.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2 // 启动swagger
//@EnableSwaggerBootstrapUI
//是否开启swagger，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），可根据springboot的多环境配置进行设置
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 方法需要有ApiOperation注解才能生存接口文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.yzm.swagger.controller"))
                // 可以根据url路径设置哪些请求加入文档，忽略哪些请求，any所有
                .paths(PathSelectors.any())
//                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build()
//                .pathMapping("/")

//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, getGlobalResponseMessage())

                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())

//                .globalOperationParameters(getGlobalParameters())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger服务")
                .description("swagger服务 API 接口文档")
                .version("1.0.0")
                .contact(new Contact("跳转网址", "https://www.baidu.com", "联系邮箱"))
                .build();
    }

    /**
     * Swagger允许通过  Docket的  globalResponseMessage（）方法全局覆盖HTTP方法的响应消息。
     * 首先，您必须指示Swagger不要使用默认响应消息。
     * 假设您希望覆盖  所有GET方法的500和403响应消息。为了达到这个目的，一些代码必须被添加到Docket的初始化块：
     */
    private List<ResponseMessage> getGlobalResponseMessage() {
        List<ResponseMessage> list = new ArrayList<>();
        list.add(new ResponseMessageBuilder()
                .code(500)
                .message("500 message 测试")
                .responseModel(new ModelRef("Error"))
                .build());
        list.add(new ResponseMessageBuilder()
                .code(403)
                .message("Forbidden! 测试")
                .build());
        return list;
    }

    /**
     * 每个接口都设置全局参数
     */
    private List<Parameter> getGlobalParameters() {
        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(
                new ParameterBuilder()
                        .name("token")
                        .description("令牌")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build());
        return parameters;
    }

    /**
     * 通过Swagger2的securitySchemes配置全局参数：
     * 如下列代码所示，securitySchemes的ApiKey中增加一个名为“Authorization”，type为“header”的参数。
     */
    private List<ApiKey> securitySchemes() {
        List<ApiKey> list = new ArrayList<>(1);
        list.add(new ApiKey("Authorization", "Authorization", "header"));
        return list;
    }

    /**
     * 在Swagger2的securityContexts中通过正则表达式，设置需要使用参数的接口（或者说，是去除掉不需要使用参数的接口），
     * 如下列代码所示，通过PathSelectors.regex("^(?!auth).*$")，所有包含"auth"的接口不需要使用securitySchemes。
     * 即不需要使用上文中设置的名为“Authorization”，type为“header”的参数。
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    /**
     * 设置完成后进入SwaggerUI，右上角出现“Authorization”按钮，点击即可输入我们配置的参数。
     * 对于不需要输入参数的接口（上文所述的包含auth的接口），在未输入Authorization参数就可以访问。
     * 其他接口则将返回401错误。点击右上角“Authorization”按钮，输入配置的参数后即可访问。参数输入后全局有效，无需每个接口单独输入。
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

}
