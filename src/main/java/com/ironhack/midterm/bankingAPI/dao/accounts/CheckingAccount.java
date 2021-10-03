package com.ironhack.midterm.bankingAPI.dao.accounts;

import com.ironhack.midterm.bankingAPI.dao.interfaces.HasPenaltyFee;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAccount extends Account implements HasPenaltyFee {
    private BigDecimal monthlyMaintenanceFee;
    private BigDecimal minimumBalance;
    private String secretKey;
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public CheckingAccount(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal monthlyMaintenanceFee, BigDecimal minimumBalance, String secretKey, Date creationDate, Status status) {
        super(balance, primaryOwner, secondaryOwner);
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
    }

    public CheckingAccount(BigDecimal balance, AccountHolder primaryOwner, BigDecimal monthlyMaintenanceFee, BigDecimal minimumBalance, String secretKey, Date creationDate, Status status) {
        super(balance, primaryOwner);
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
    }

    @Override
    public boolean isActive() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public void activateAccount() {
        setStatus(Status.ACTIVE);
    }

    @Override
    public boolean secretKeyIsValid(String secretKey) {
        return this.secretKey.equals(secretKey);
    }

    @Override
    public void freezeAccount() {
        setStatus(Status.FROZEN);
    }

    @Override
    public boolean balanceBelowMinimum() {
        return getBalance().compareTo(minimumBalance)==-1;
    }

    @Override
    public void chargeFee() {
        setBalance(getBalance().subtract(getPenaltyFee()));
    }
}
