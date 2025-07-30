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

    @Override
    public Flux<Account> findAllAccounts() {
        log.info("findAllAccounts called from accountAdapter");
        return accountRepository.findAll()
                .map(
                acE -> Account
                        .builder()
                        .id(acE.getId())
                        .accountNumber(acE.getAccountNumber())
                        .balance(acE.getBalance())
                        .mobileNumber(acE.getMobileNumber())
                        .email(acE.getEmail())
                        .accountType(acE.getAccountType())
                        .bankName(acE.getBankName())
                        .branchName(acE.getBranchName())
                        .build()
        );
    }

    @Override
    public Mono<Account> findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).map(
                acE -> Account
                        .builder()
                        .id(acE.getId())
                        .accountNumber(acE.getAccountNumber())
                        .balance(acE.getBalance())
                        .mobileNumber(acE.getMobileNumber())
                        .email(acE.getEmail())
                        .accountType(acE.getAccountType())
                        .bankName(acE.getBankName())
                        .branchName(acE.getBranchName())
                        .build()
        );
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        // convert the model to an entity
        AccountEntity accountEntity = AccountEntity
                .builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .mobileNumber(account.getMobileNumber())
                .email(account.getEmail())
                .accountType(account.getAccountType())
                .bankName(account.getBankName())
                .branchName(account.getBranchName())
                .isNewRecord(true)
                .build();

        // This is enabling the mobile number to save in the database.


        System.out.println("Received in Service and converted to domain to entity" + accountEntity.toString());

        return accountRepository.save(accountEntity).map(
                acE -> Account
                        .builder()
                        .id(acE.getId())
                        .accountNumber(acE.getAccountNumber())
                        .balance(acE.getBalance())
                        .mobileNumber(acE.getMobileNumber())
                        .email(acE.getEmail())
                        .accountType(acE.getAccountType())
                        .bankName(acE.getBankName())
                        .branchName(acE.getBranchName())
                        .build()
        );
    }

    @Override
    public Mono<Account> updateAccount(String accountNumber, Account account) {
        return null;
    }


    @Override
    public Mono<Void> deleteAccountByAccountNumber(String accountNumber) {
        return null;
    }

    @Override
    public Mono<BigDecimal> checkBalance(String accountNumber) {
        return null;
    }

    @Override
    public Mono<BigDecimal> addBalance(Account account, BigDecimal amount) {
        return null;
    }

    //Convert Entity to Domain
}
