package com.rafiyad.bank.features.account.adapter.in.router;


import com.rafiyad.bank.features.account.adapter.in.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class AccountRouter {
    private String baseUrl = "/api/v1/accounts";
    private String accountNumber = "/{accountNumber}";
    private String mobileNumber = "/{mobileNumber}";

    @Bean
    public RouterFunction<ServerResponse> route(AccountHandler accountHandler) {
        return RouterFunctions.route()
                .nest(RequestPredicates.accept(MediaType.APPLICATION_JSON),
                        builder -> builder
                                .GET(baseUrl, accountHandler::getAllAccounts)
                                .GET(baseUrl.concat(accountNumber), accountHandler::findAccountByAccountNumber)
                                .POST(baseUrl, accountHandler::createAccount)
                                //.PUT(baseUrl.concat(id), accountHandler::updateAccount)
                                //.DELETE(baseUrl.concat(id).concat(mobileNumber), accountHandler::deleteAccount)

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
