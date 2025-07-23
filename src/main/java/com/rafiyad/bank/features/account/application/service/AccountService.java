package com.rafiyad.bank.features.account.application.service;


import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.AccountRequestDto;
import com.rafiyad.bank.features.account.application.port.in.dto.response.AccountResponseDto;
import com.rafiyad.bank.features.account.application.port.out.AccountPersistencePort;
import com.rafiyad.bank.features.account.domain.Account;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService implements AccountUseCase {
    private final AccountPersistencePort accountPersistencePort;
    private final MappingContext mappingContext;

    public AccountService(AccountPersistencePort accountPersistencePort, MappingContext mappingContext) {
        this.accountPersistencePort = accountPersistencePort;
        this.mappingContext = mappingContext;
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
    public Mono<AccountResponseDto> addNumber(AccountRequestDto accountRequestDto) {
        return null;
    }

    @Override
    public Flux<AccountResponseDto> findAllAccounts() {
        return accountPersistencePort.findAllAccounts().flatMap(i ->
                Mono.just(AccountResponseDto
                        .builder()
                        .accountNumber(i.getAccountNumber())
                        .balance(i.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP))
                        .build()))
                //.onErrorResume(e -> e.getMessage()==null, e-> Mono.empty());
                //.onErrorResume(e-> Mono.empty());
                .onErrorResume(WebClientException.class, e-> Mono.empty())
                .switchIfEmpty(Mono.error(new Exception("No data found")));
    }

    @Override
    public Mono<AccountResponseDto> findAccountByAccountNumber(String accountNumber) {
        //System.out.println("Request received from router to Service:");
        return accountPersistencePort.findAccountByAccountNumber(accountNumber)
                .map(ac -> new AccountResponseDto(ac.getAccountNumber(), ac.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP)))
                .doOnError(throwable -> System.out.println("Error occurred at while getting " +accountNumber + " " + throwable.getMessage()))
                .switchIfEmpty(Mono.error(new Exception("No data found with "+ accountNumber)));
    }

    @Override
    public Mono<BigDecimal> checkBalance() {
        return null;
    }

    @Override
    public Mono<AccountResponseDto> createAccount(AccountRequestDto accountRequestDto) {
        //Converting the RequestDto to Domain
        Account account = Account
                .builder()
                .id(UUID.randomUUID().toString())
                .accountNumber(UUID.randomUUID().toString())
                .balance(BigDecimal.ZERO)
                .accountType(accountRequestDto.getAccountType())
                .bankName(accountRequestDto.getBankName())
                .build();


        return accountPersistencePort
                .createAccount(account) // Converting the domain to responseDto
                .map(ac -> new AccountResponseDto(ac.getAccountNumber(), ac.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP)));

    }

    @Override
    public Mono<BigDecimal> addBalance(Account account, BigDecimal amount) {
        return null;
    }
}
