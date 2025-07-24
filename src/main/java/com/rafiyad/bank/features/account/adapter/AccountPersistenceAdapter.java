package com.rafiyad.bank.features.account.adapter;

import com.rafiyad.bank.features.account.adapter.out.entity.AccountEntity;
import com.rafiyad.bank.features.account.adapter.out.repository.AccountRepository;
import com.rafiyad.bank.features.account.application.port.out.AccountPersistencePort;
import com.rafiyad.bank.features.account.domain.Account;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@Builder
@Slf4j
public class AccountPersistenceAdapter implements AccountPersistencePort {
    private final AccountRepository accountRepository;

    public AccountPersistenceAdapter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Flux<Account> findAllAccounts() {
        return accountRepository.findAll().map(ac -> Account.builder()
                .id(ac.getId())
                .accountNumber(ac.getAccountNumber())
                .balance(ac.getBalance())
                .accountType(ac.getAccountType())
                .bankName(ac.getBankName())
                .build())
                .doOnError(throwable -> System.out.println("Error occurred at adapter " + throwable.getMessage()));
    }

    @Override
    public Mono<Account> findAccountByAccountNumber(String accountNumber) {
       // System.out.println("Request received from router to service to adapter");
        return accountRepository.findByAccountNumber(accountNumber)
                .map(ac -> Account.builder()
                        .accountNumber(ac.getAccountNumber())
                        .balance(ac.getBalance())
                        .accountType(ac.getAccountType())
                        .bankName(ac.getBankName())
                        .build())
                .doOnError(throwable -> System.out.println("Error occurred at while getting " +accountNumber + " " + throwable.getMessage()));
    }

    @Override
    public Mono<BigDecimal> checkBalance(String accountNumber) {
        return null;
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        //Convert the Domain to Entity
        log.info("Create account method called in adapter before save {}", account);
        System.out.println("Request received from router to service to adapter");
        AccountEntity entity =AccountEntity
                .builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .mobileNumber(account.getMobileNumber())
                .email(account.getEmail())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .bankName(account.getBankName())
                .build();

        entity.setNewRecord(true); // Use the new setter

        System.out.println(entity.toString());

        return accountRepository.save(entity)
                .switchIfEmpty(Mono.error(new Exception("No data found")))
                .map(ac -> Account
                        .builder()
                        .id(ac.getId())
                        .accountNumber(ac.getAccountNumber())
                        .mobileNumber(ac.getMobileNumber())
                        .email(ac.getEmail())
                        .balance(ac.getBalance())
                        .accountType(ac.getAccountType())
                        .bankName(ac.getBankName())
                        .build());
    }



    @Override
    public Mono<BigDecimal> addBalance(Account account, BigDecimal amount) {
        return null;
    }


    //Convert Entity to Domain

}
