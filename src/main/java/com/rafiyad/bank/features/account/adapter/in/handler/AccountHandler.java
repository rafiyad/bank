package com.rafiyad.bank.features.account.adapter.in.handler;


import ch.qos.logback.core.net.SocketConnector;
import com.rafiyad.bank.core.ExceptionHandlerUtil;
import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.RequestDto;
import com.rafiyad.bank.features.account.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Component
public class AccountHandler {
    private final AccountUseCase accountUseCase;

    public AccountHandler(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    public Mono<ServerResponse> getAllAccounts (ServerRequest serverRequest){
        //ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(accountUseCase.findAllAccounts(), Account.class);
        return accountUseCase.findAllAccounts().collectList()
                .flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(item))
                .onErrorResume(throwable -> {
                    System.out.println("Error occurred while getting accounts: " + throwable.getMessage());
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("Something went wrong" + throwable.getMessage());
                });
    }

    public Mono<ServerResponse> findAccountByAccountNumber(ServerRequest serverRequest) {
        String accountNumber = serverRequest.pathVariable("accountNumber");
        //System.out.println("Request received from router:");
        return accountUseCase.findAccountByAccountNumber(accountNumber)
                .flatMap(item -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(item))
                .onErrorResume(throwable -> {
                    System.out.println("Error occurred while getting accounts: " + throwable.getMessage());
                    return ServerResponse.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("Something went wrong with account you are looking for" + throwable.getMessage());
                });
    }


    public Mono<ServerResponse> numbers(ServerRequest serverRequest) {
        return accountUseCase.getNumbers()
                .flatMap(responseDto ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(responseDto));
    }

}
