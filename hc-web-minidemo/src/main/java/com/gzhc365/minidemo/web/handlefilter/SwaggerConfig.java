package com.gzhc365.minidemo.web.handlefilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.gzhc365.minidemo.web.controller"})
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket api() {
//        /List<Parameter> pars = new ArrayList<Parameter>();
//        pars.add(getParameter("login_access_token", "令牌", "String").build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/user/.*"))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ParameterBuilder getParameter(String name, String desc, String type) {
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(name).description(desc).modelRef(new ModelRef(type)).parameterType("header").required(true).build();
        return tokenPar;
    }
//
//    @Bean
//    public Docket customDocket() {
//        //
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo());
//    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("小刚", "", "");
        return new ApiInfo("API接口",//大标题 title
                "Swagger",//小标题
                "0.0.1",//版本
                "",//termsOfServiceUrl
                contact,//作者
                "",//链接显示文字
                ""//网站链接
        );
    }


}