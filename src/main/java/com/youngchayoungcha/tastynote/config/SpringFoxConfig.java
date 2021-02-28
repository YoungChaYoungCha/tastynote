package com.youngchayoungcha.tastynote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
*  Swagger2 설정
*/
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // ApiSelectorBuilder를 리턴
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)) // API 문서를 만들어줄 범위를 지정
                .paths(PathSelectors.any()) // API의 URL 경로를 지정
                .build();
    }
}
