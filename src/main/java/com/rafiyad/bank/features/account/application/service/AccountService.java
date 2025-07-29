package com.rafiyad.bank.features.account.application.service;


import com.rafiyad.bank.features.account.application.port.in.AccountUseCase;
import com.rafiyad.bank.features.account.application.port.in.dto.request.AccountRequestDto;
import com.rafiyad.bank.features.account.application.port.in.dto.response.AccountResponseDto;
import com.rafiyad.bank.features.account.application.port.out.AccountPersistencePort;
import com.rafiyad.bank.features.account.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {

    private final AccountPersistencePort accountPersistencePort;


    // It will generate 14 digit unique id
    private static String generateRandomAccountNumber() {
        long timestamp = System.currentTimeMillis();
        int random = new Random().nextInt(10);
        return String.format("%013d%d", timestamp, random);
    }

    // It will generate 15 digit unique id
    private static String generateRandomAccountId() {
        long timestamp = System.currentTimeMillis(); // 13 digits
        int randomTwoDigits = new Random().nextInt(90) + 10; // 10–99
        return String.format("%d%d", randomTwoDigits, timestamp); // total 15 digits
    }

    @Override
    public Flux<AccountResponseDto> findAllAccounts() {
        log.info("findAllAccounts called from accountService");
        return accountPersistencePort.findAllAccounts().map(
                ac-> AccountResponseDto
                        .builder()
                        .accountNumber(ac.getAccountNumber())
                        .balance(ac.getBalance())
                        .accountType(ac.getAccountType())
                        .branchName(ac.getBranchName())
                        .bankName(ac.getBankName())
                        .build());
    }

    @Override
    public Mono<AccountResponseDto> findAccountByAccountNumber(String accountNumber) {
        return accountPersistencePort.findAccountByAccountNumber(accountNumber).map(
                ac-> AccountResponseDto
                        .builder()
                        .accountNumber(ac.getAccountNumber())
                        .balance(ac.getBalance())
                        .accountType(ac.getAccountType())
                        .branchName(ac.getBranchName())
                        .bankName(ac.getBankName())
                        .build())
                .switchIfEmpty(Mono.error(new RuntimeException("Account not found with this id "+ accountNumber)));
    }

    @Override
    public Mono<AccountResponseDto> createAccount(AccountRequestDto accountRequestDto) {

        // Check if the account already exists or not if yes through an exception

        Account account = Account
                .builder()
                .id(generateRandomAccountId())
                .accountNumber(generateRandomAccountNumber())
                .mobileNumber(accountRequestDto.getMobileNumber())
                .email(accountRequestDto.getEmail())
                .balance(BigDecimal.ZERO)
                .accountType(accountRequestDto.getAccountType())
                .branchName(accountRequestDto.getBranchName())
                .bankName(accountRequestDto.getBankName())
                .build();

        return accountPersistencePort.createAccount(account).map(
                ac -> AccountResponseDto
                        .builder()
                        .accountNumber(ac.getAccountNumber())
                        .balance(ac.getBalance())
                        .accountType(ac.getAccountType())
                        .branchName(ac.getBranchName())
                        .bankName(ac.getBankName())
                        .build());
    }

    @Override
    public Mono<AccountResponseDto> deleteAccountByAccountNumber(String accountNumber, String mobileNumber) {
        return null;
    }

    @Override
    public Mono<AccountResponseDto> updateAccountByMobileNumber(String accountNumber, AccountRequestDto accountRequestDto) {
        return null;
    }

    @Override
    public Mono<BigDecimal> checkBalance() {
        return null;
    }

    @Override
    public Mono<BigDecimal> addBalance(Account account, BigDecimal amount) {
        return null;
    }




}


