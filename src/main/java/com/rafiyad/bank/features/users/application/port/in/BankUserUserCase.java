package com.rafiyad.bank.features.users.application.port.in;


import com.rafiyad.bank.features.users.domain.BankUser;
import reactor.core.publisher.Mono;

public interface BankUserUserCase {
    Mono<BankUser> getUserByUsername(String username);
    Mono<BankUser> getUserByAccountNumber(String accountNumber);
    Mono<BankUser> createUser(BankUser bankUser);
    Mono<BankUser> updateUser(BankUser bankUser);
    Mono<Boolean> deleteUser(String username);

}
