package com.org.carder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info (title = "EMP API",version = "2.0",description = "test"))
public class CarderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarderApplication.class, args);
    }

}
