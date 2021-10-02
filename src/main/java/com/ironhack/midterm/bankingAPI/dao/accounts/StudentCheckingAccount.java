package com.ironhack.midterm.bankingAPI.dao.accounts;

import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCheckingAccount extends Account {
    private String secretKey;
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public StudentCheckingAccount(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, Date creationDate, Status status) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
    }

    public StudentCheckingAccount(BigDecimal balance, AccountHolder primaryOwner, String secretKey, Date creationDate, Status status) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
    }

    @Override
    public boolean isActive() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public void freezeAccount() {
        setStatus(Status.FROZEN);
    }
}
