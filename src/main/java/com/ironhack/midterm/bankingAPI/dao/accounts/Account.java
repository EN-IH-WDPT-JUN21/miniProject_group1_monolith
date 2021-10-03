package com.ironhack.midterm.bankingAPI.dao.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Balance cannot be empty")
    private BigDecimal balance;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    @NotNull(message = "Primary owner must be given!")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "sec_owner_id")
    private AccountHolder secondaryOwner;

    private BigDecimal penaltyFee;

    public Account(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = new BigDecimal("40");
    }

    public Account(BigDecimal balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.penaltyFee = new BigDecimal("40");
    }
    @JsonIgnore
    public boolean isActive(){return true;}
    @JsonIgnore
    public void freezeAccount(){}
    @JsonIgnore
    public void activateAccount(){}
    @JsonIgnore
    public boolean secretKeyIsValid(String secretKey){return false;}
}
