package com.volkovt.administrativo.config;

import org.springframework.context.annotation.*;

import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.volkovt.administrativo.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
                	
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Produtos API")
                .description("Api do projeto de produtos")
                .version("1.0")
                .contact(contact())
                .build();
    }

    private Contact contact() {
        return new Contact("Diego Melo", "http://github.com/volkovt", "diego_melo_1991@hotmail.com");
    }
}
