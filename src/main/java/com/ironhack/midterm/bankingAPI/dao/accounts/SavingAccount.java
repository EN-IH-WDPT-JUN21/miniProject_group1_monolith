package com.ironhack.midterm.bankingAPI.dao.accounts;

import com.ironhack.midterm.bankingAPI.dao.interfaces.HasPenaltyFee;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccount extends Account implements HasPenaltyFee {

    @DecimalMin(value = "0", message = "Interest rate cannot be negative")
    @DecimalMax(value = "0.5", message = "The maximum interest rate is 0.5")
    private BigDecimal interestRate;

    @DecimalMin(value="100",message="Minimum balance for this account is 100")
    private BigDecimal minimumBalance;

    private String secretKey;
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public SavingAccount(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal interestRate, BigDecimal minimumBalance, String secretKey, Date creationDate, Status status) {
        super(balance, primaryOwner, secondaryOwner);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
    }

    public SavingAccount(BigDecimal balance, AccountHolder primaryOwner, BigDecimal interestRate, BigDecimal minimumBalance, String secretKey, Date creationDate, Status status) {
        super(balance, primaryOwner);
        this.interestRate = interestRate;
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
    public void freezeAccount() {
        setStatus(Status.FROZEN);
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
    public boolean balanceBelowMinimum() {
        return getBalance().compareTo(minimumBalance)==-1;
    }

    @Override
    public void chargeFee() {
        setBalance(getBalance().subtract(getPenaltyFee()));
    }
}
