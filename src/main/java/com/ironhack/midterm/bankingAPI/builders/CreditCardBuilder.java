package com.ironhack.midterm.bankingAPI.builders;

import com.ironhack.midterm.bankingAPI.dao.accounts.CreditCard;
import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.CreditCardDTO;
import com.ironhack.midterm.bankingAPI.dto.SavingAccountDTO;
import com.ironhack.midterm.bankingAPI.enums.Status;

import java.math.BigDecimal;
import java.util.Date;

public class CreditCardBuilder {
    public static CreditCard build(CreditCardDTO creditCardDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        CreditCard creditCard = buildFromDTO(creditCardDTO, primaryOwner);
        creditCard.setSecondaryOwner(secondaryOwner);
        return creditCard;
    }

    public static CreditCard build(CreditCardDTO creditCardDTO, AccountHolder primaryOwner) {
        return buildFromDTO(creditCardDTO, primaryOwner);
    }


    private static CreditCard buildFromDTO(CreditCardDTO creditCardDTO, AccountHolder primaryOwner) {
        BigDecimal balance;
        BigDecimal interestRate;
        BigDecimal creditLimit;

        //balance
        if (creditCardDTO.getBalance() == null) {
            throw new IllegalStateException("balance cannot be null");
        } else {
            balance = creditCardDTO.getBalance();
        }
        //Primary owner validation
        if (primaryOwner == null) {
            throw new IllegalStateException("Account must have an owner");
        }
        //interestRate
        if (creditCardDTO.getInterestRate() == null) {
            interestRate = new BigDecimal("0.2");
        } else {
            interestRate = creditCardDTO.getInterestRate();
            //-1 means the interest rate is less than 0.1
            if (interestRate.compareTo(new BigDecimal("0.1")) == -1) {
                throw new IllegalStateException("Interest rate cannot be less then 0.1");
            }
        }
        //Credit limit
        if (creditCardDTO.getCreditLimit() == null) {
            creditLimit = new BigDecimal("100");
        } else {
            creditLimit = creditCardDTO.getInterestRate();
            //1 means the credit limit is above 100000
            if (creditLimit.compareTo(new BigDecimal("100000")) == 1) {
                throw new IllegalStateException("Credit limit cannot be above 100000");
            }
        }
        return new CreditCard(balance,primaryOwner,creditLimit,interestRate);

    }
}
