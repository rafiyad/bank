package com.rafiyad.bank.features.account.adapter.out.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account")
public class AccountEntity implements Persistable<String> {
    // Persistable is added for forcing the id to be created by application, not by the database
    /*
    Spring Data provides the org.springframework.data.domain.Persistable<ID> interface.
    Implementing this interface allows entities to explicitly define their identity (getId())
    and whether they are new or already persisted (isNew()). This can be particularly useful
    for optimizing save operations, as it can help Spring Data determine whether to perform
    an INSERT or an UPDATE without necessarily querying the database first.
    */

    @Id
    private String id;

    @NotNull
    @UniqueElements
    private String accountNumber;

    @NotNull
    private BigDecimal balance;

    @NotNull
    @UniqueElements
    private String mobileNumber;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String accountType;

    @NotNull
    private String bankName;

    @NotNull
    private String branchName;

    // This is flag has to be true inorder to save the ID
    @Transient
    private boolean isNewRecord;


    @Override
    public boolean isNew() {
     return this.isNewRecord || id == null;
    }
}
