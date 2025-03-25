//package com.example.daraja.payment.controllers;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//@Configuration
//public class TestRouter {
//    @Bean
//    public RouterFunction<ServerResponse> testRoute() {
//        return RouterFunctions.route()
//                .GET("/test", req -> {
//                    System.out.println("Test endpoint called!");
//                    return ServerResponse.ok().bodyValue("Test endpoint working");
//                })
//                .build();
//    }
//}