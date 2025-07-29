package com.rafiyad.bank.features.account.adapter.in.handler;


import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.AccountRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountHandler {

    private final AccountUseCase accountUseCase;

    public Mono<ServerResponse> getAllAccounts(ServerRequest serverRequest) {
        log.info("getAllAccounts called from accountHandler");
        return accountUseCase.findAllAccounts().collectList().flatMap(
                ac-> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ac));
    }

    public Mono<ServerResponse> findAccountByAccountNumber (ServerRequest serverRequest) {
        log.info("findAccountByAccountNumber called from accountHandler");
        String accountNumber = serverRequest.pathVariable("accountNumber");

        return accountUseCase.findAccountByAccountNumber(accountNumber).flatMap(
                ac -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ac))
                /*.onErrorResume(i -> {
            return ServerResponse.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue("Account not found with accountNumber: " + accountNumber);
        })*/
        ;
    }
    public Mono<ServerResponse> createAccount (ServerRequest serverRequest) {
        //Mono<AccountRequestDto> accountRequestDto = serverRequest.bodyToMono(AccountRequestDto.class);
        return serverRequest.bodyToMono(AccountRequestDto.class)
                .flatMap(requestDto -> {
                    log.info("Received account creation request: {}", requestDto);
                    return accountUseCase.createAccount(requestDto).flatMap(
                            ac -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(ac));
                });
    }


}
