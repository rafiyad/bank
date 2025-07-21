package com.rafiyad.bank.features.account.adapter.in.handler;


import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.RequestDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class AccountHandler {
    private final AccountUseCase accountUseCase;

    public AccountHandler(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    public Mono<ServerResponse> numbers(ServerRequest serverRequest) {
        return accountUseCase.getNumbers()
                .flatMap(responseDto ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(responseDto));
    }
    public Mono<ServerResponse> checkIfNumberExists(ServerRequest serverRequest){

        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        return accountUseCase.numbersFind(id)
                .flatMap(responseDto -> {
                    return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(responseDto);
                });
    }

    public Mono<ServerResponse> addNumbers (ServerRequest serverRequest){
        return serverRequest.bodyToMono(RequestDto.class)
                .flatMap(requestDto ->
                        accountUseCase.addNumber(requestDto)
                                .flatMap(responseDto ->
                                        ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(responseDto)
                                )
                );
    }

}
