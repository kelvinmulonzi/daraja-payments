package com.example.daraja.payment.configuration;

import com.example.daraja.payment.configuration.C2BHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
@CrossOrigin(value = "*")
@Configuration
public class C2BRouter {

    @Bean
    public RouterFunction<ServerResponse> route(C2BHandler handler) {
        return RouterFunctions.route(POST("/api/daraja/c2b/callback"), handler::handleC2BCallback);
    }
}
