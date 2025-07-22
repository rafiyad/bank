package com.rafiyad.bank.features.account.application.service;


import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.RequestDto;
import com.rafiyad.bank.features.account.application.port.in.dto.response.AccountResponseDto;
import com.rafiyad.bank.features.account.application.port.out.AccountPersistencePort;
import com.rafiyad.bank.features.account.domain.Account;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class AccountService implements AccountUseCase {
    private final AccountPersistencePort accountPersistencePort;

    public AccountService(AccountPersistencePort accountPersistencePort) {
        this.accountPersistencePort = accountPersistencePort;
    }

   // Flux<Integer> originalFlux = Flux.range(1, 10);

/*    @Override
    public Mono<ResponseDto> getNumbers() {
        return originalFlux
                .collectList()
                .map(integers -> ResponseDto.builder()
                        .message("Numbers fetched successfully")
                        .data(integers)
                        .build());
    }*/

   /* @Override
    public Mono<ResponseDto> numbersFind(Integer id) {
        return originalFlux
                .filter(i -> i.equals(id))
                .next()
                .map(i -> ResponseDto.builder()
                        .message("Number fetched successfully")
                        .data(List.of(i))
                        .build())
        // returns 200 ok but with empty
                .defaultIfEmpty(ResponseDto.builder()
                        .message("Not found")
                        .data(List.of(-1))
                        .build());
    }*/

   /* @Override
    public Mono<ResponseDto> addNumber(RequestDto requestDto) {
        Flux<Integer> newFlux = Flux.fromIterable(requestDto.getData());
        return originalFlux
                .concatWith(newFlux)
                .distinct()
                .collectList()
                .map(allNumber -> ResponseDto.builder()
                            .message("Number added successfully")
                            .data(allNumber)
                            .build());
    }*/

    @Override
    public Mono<AccountResponseDto> getNumbers() {
        return null;
    }

    @Override
    public Mono<AccountResponseDto> numbersFind(Integer id) {
        return null;
    }

    @Override
    public Mono<AccountResponseDto> addNumber(RequestDto requestDto) {
        return null;
    }

    @Override
    public Flux<AccountResponseDto> findAllAccounts() {
        return accountPersistencePort.findAllAccounts().flatMap(i ->
                Mono.just(AccountResponseDto
                        .builder()
                        .accountNumber(i.getAccountNumber())
                        .balance(i.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP))
                        .build()));
    }

    @Override
    public Mono<BigDecimal> checkBalance() {
        return null;
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        return null;
    }

    @Override
    public Mono<BigDecimal> addBalance(Account account, BigDecimal amount) {
        return null;
    }
}
