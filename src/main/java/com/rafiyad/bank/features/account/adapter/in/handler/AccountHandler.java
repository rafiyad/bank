package com.rafiyad.bank.features.account.adapter.in.handler;


import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.AccountRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AccountHandler {
    private final AccountUseCase accountUseCase;
    private final WebClient.Builder builder;

    public AccountHandler(AccountUseCase accountUseCase, WebClient.Builder builder) {
        this.accountUseCase = accountUseCase;
        this.builder = builder;
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

    public Mono<ServerResponse> createAccount(ServerRequest serverRequest){
        log.info("Create account method called");
        Mono<AccountRequestDto> requestDtoMono = serverRequest.bodyToMono(AccountRequestDto.class);
        //Extract the data from body
        //send the Dto to service
        // Service will convert the Dto to domain
        // Adapter will convert the domain to Entity
        String searchKey = serverRequest.queryParam("qr").orElse("");
        System.out.println("Handler Called");
            return requestDtoMono
                    .map(accountRequestDto -> {
                        accountRequestDto.setSearchKey(searchKey);
                        return accountRequestDto;
                    })
                    .flatMap(accountRequestDto -> accountUseCase.createAccount(accountRequestDto))
                    .flatMap(res ->
                            ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(res));
    }

}
