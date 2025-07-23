package com.rafiyad.bank.features.account.adapter.out.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account")
public class AccountEntity {
    @Id
    private String id;

    @NotNull
    @UniqueElements
    private String accountNumber;

    @NotNull
    @UniqueElements
    private String mobileNumber;

    @Email
    @NotNull
    private String email;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private String accountType;

    @NotNull
    private String bankName;
}
