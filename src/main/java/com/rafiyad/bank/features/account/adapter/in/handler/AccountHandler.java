package com.rafiyad.bank.features.account.adapter.in.handler;


import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.RequestDto;
import com.rafiyad.bank.features.account.domain.Account;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AccountHandler {
    private final AccountUseCase accountUseCase;

    public AccountHandler(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    public Mono<ServerResponse> getAllAccounts (ServerRequest serverRequest){
        //ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(accountUseCase.findAllAccounts(), Account.class);
        return accountUseCase.findAllAccounts().collectList().flatMap(item ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(item)
        );
    }

    public Mono<ServerResponse> findAccountByAccountNumber(ServerRequest serverRequest) {
        String accountNumber = serverRequest.pathVariable("accountNumber");
        System.out.println("Request received from router:");
        return accountUseCase.findAccountByAccountNumber(accountNumber)
                .flatMap(item -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(item));
    }


    public Mono<ServerResponse> numbers(ServerRequest serverRequest) {
        return accountUseCase.getNumbers()
                .flatMap(responseDto ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(responseDto));
    }

}
