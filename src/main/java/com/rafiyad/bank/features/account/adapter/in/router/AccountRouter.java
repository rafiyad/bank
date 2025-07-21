package com.rafiyad.bank.features.account.adapter.in.router;


import com.rafiyad.bank.features.account.adapter.in.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class AccountRouter {
    private String baseUrl = "/api/v1/number";
    private String id = "/{id}";

    @Bean
    public RouterFunction<ServerResponse> route(AccountHandler accountHandler) {
        return RouterFunctions.route()
                .nest(RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        builder -> builder
                                .GET("/api/v1/number", accountHandler::numbers)
                                .GET(baseUrl.concat(id), accountHandler::checkIfNumberExists)
                                .POST(baseUrl, accountHandler::addNumbers)
//                                .GET("/api/v1/number/".concat(id), accountHandler::numbersFind)
                                .build())
                /*.nest(RequestPredicates.accept(MediaType.MULTIPART_FORM_DATA),
                        builder -> builder
                                .GET("/spi/v1/number", accountHandler::numbers)
                                .GET("/spi/v1/number", accountHandler::numbers)
                                .GET("/spi/v1/number", accountHandler::numbers)
                                .GET("/spi/v1/number", accountHandler::numbers)
                                .build())*/
                .build();
    }
}
