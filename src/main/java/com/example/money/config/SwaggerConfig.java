package com.example.money.config;

import com.example.money.domain.ApiCode;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.money"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo())
                .useDefaultResponseMessages(false);

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Money Transfer API",
                "Money Transfer API",
                "1.0",
                "Terms of service",
                new Contact("Sunny Lo", "", "spring85658@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
