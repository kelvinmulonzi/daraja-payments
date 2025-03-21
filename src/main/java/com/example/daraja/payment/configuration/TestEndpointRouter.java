package com.example.daraja.payment.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.logging.Logger;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class TestEndpointRouter {

    private static final Logger logger = Logger.getLogger(TestEndpointRouter.class.getName());

    @Bean
    public RouterFunction<ServerResponse> helloRoute() {
        logger.info("Registering /hello test endpoint");

        return RouterFunctions
                .route(GET("/hello"), request -> {
                    logger.info("Hello endpoint called!");
                    return ServerResponse.ok()
                            .contentType(MediaType.TEXT_PLAIN)
                            .bodyValue("Hello from WebFlux!");
                });
    }
}
