package com.ironhack.midterm.bankingAPI.builders;

import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.SavingAccountDTO;
import com.ironhack.midterm.bankingAPI.enums.Status;

import java.math.BigDecimal;
import java.util.Date;

public class SavingAccountBuilder {
    public static SavingAccount build(SavingAccountDTO savingAccountDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner){
        SavingAccount savingAccount = buildFromDTO(savingAccountDTO,primaryOwner);
        savingAccount.setSecondaryOwner(secondaryOwner);
        return savingAccount;
    }
    public static SavingAccount build(SavingAccountDTO savingAccountDTO, AccountHolder primaryOwner){
        return buildFromDTO(savingAccountDTO,primaryOwner);
    }


    private static SavingAccount buildFromDTO(SavingAccountDTO savingAccountDTO, AccountHolder primaryOwner){
        BigDecimal balance;
        BigDecimal interestRate;
        BigDecimal minimumBalance;
        String secretKey;
        Date creationDate;

        //balance
        if (savingAccountDTO.getBalance()==null){
            throw new IllegalStateException("balance cannot be null");
        }
        else {
            balance = savingAccountDTO.getBalance();
        }
        //Primary owner validation
        if (primaryOwner==null){
            throw new IllegalStateException("Account must have an owner");
        }
        //interestRate
        if (savingAccountDTO.getInterestRate()==null){
            interestRate=new BigDecimal("0.0025");
        }
        else{
            interestRate=savingAccountDTO.getInterestRate();
        }
        //minimumBalance
        if (savingAccountDTO.getMinimumBalance()==null){
            minimumBalance=new BigDecimal("1000");
        }
        else{
            minimumBalance=savingAccountDTO.getMinimumBalance();
            //-1 means minimumBalance is less than 100
            if (minimumBalance.compareTo(new BigDecimal("100"))==-1){
                throw new IllegalStateException("Minimum balance cannot be less then 100");
            }
        }
        //secretKey
        if (savingAccountDTO.getSecretKey()==null){
            throw new IllegalStateException("Secret key cannot be null");
        }
        else {
            secretKey = savingAccountDTO.getSecretKey();
        }
        //creation date
        creationDate=new Date();

        return new SavingAccount(balance,primaryOwner,interestRate,minimumBalance,secretKey,creationDate,Status.ACTIVE);


    }
}
