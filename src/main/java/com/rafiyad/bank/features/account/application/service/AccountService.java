package com.rafiyad.bank.features.account.application.service;


import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.AccountRequestDto;
import com.rafiyad.bank.features.account.application.port.in.dto.response.AccountResponseDto;
import com.rafiyad.bank.features.account.application.port.out.AccountPersistencePort;
import com.rafiyad.bank.features.account.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Random;


@Slf4j
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

        log.info("Create account method called in service before save");
        Account account = Account
                .builder()
                .id(accountRequestDto.getMobileNumber())
                .accountNumber(generateAccountNumber())
                .mobileNumber(accountRequestDto.getMobileNumber())
                .email(accountRequestDto.getEmail())
                .balance(BigDecimal.ZERO)
                .accountType(accountRequestDto.getAccountType())
                .bankName(accountRequestDto.getBankName())
                .build();

        System.out.println(account.toString());

        return accountPersistencePort //check if the account is existing or not
                .createAccount(account) // Converting the domain to responseDto
                .map(ac -> AccountResponseDto
                        .builder()
                        .accountNumber(ac.getAccountNumber())
                        .balance(ac.getBalance().setScale(2, BigDecimal.ROUND_HALF_UP))
                        .build())
                .switchIfEmpty(Mono.error(new Exception("No data found")));
    }

    @Override
    public Mono<AccountResponseDto> updateAccountByMobileNumber(String mobileNumber, AccountRequestDto accountRequestDto) {
        return null;
    }

    @Override
    public Mono<BigDecimal> addBalance(Account account, BigDecimal amount) {
        return null;
    }

    @Override
    public Mono<AccountResponseDto> deleteAccountByAccountNumber(String accountNumber, String mobileNumber) {
         //System.out.println("Request received from router to Service:");
        // Check if the account is exist and if don't
        return accountPersistencePort.deleteAccountByAccountNumber(accountNumber)
                .doOnError(throwable -> System.out.println("Error occurred at while getting " +accountNumber + " " + throwable.getMessage()))
                .switchIfEmpty(Mono.error(new Exception("No data found with "+ accountNumber)))
                .then(Mono.empty());
    };

    private static String generateAccountNumber() {
        long timestamp = System.currentTimeMillis(); // 13 digits
        int random = new Random().nextInt(10);       // 0–9 → 1 digit
        return String.format("%013d%d", timestamp, random); // total 14 digits
    }



}


