package com.rafiyad.bank.features.users.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankUser {
    private Long id;
    private String username;
    private String accountNumber;
    private String mobileNumber;
    private String accountType;
}
