package com.ironhack.midterm.bankingAPI.builders;

import com.ironhack.midterm.bankingAPI.dao.accounts.CheckingAccount;
import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dao.accounts.StudentCheckingAccount;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.CheckingAccountDTO;
import com.ironhack.midterm.bankingAPI.dto.SavingAccountDTO;
import com.ironhack.midterm.bankingAPI.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class CheckingAccountBuilder {
    public static CheckingAccount build(CheckingAccountDTO checkingAccountDTO, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        CheckingAccount checkingAccount = buildFromDTO(checkingAccountDTO, primaryOwner);
        checkingAccount.setSecondaryOwner(secondaryOwner);
        return checkingAccount;
    }

    public static CheckingAccount build(CheckingAccountDTO checkingAccountDTO, AccountHolder primaryOwner) {
        return buildFromDTO(checkingAccountDTO, primaryOwner);
    }


    private static CheckingAccount buildFromDTO(CheckingAccountDTO checkingAccountDTO, AccountHolder primaryOwner) {
        BigDecimal balance;
        BigDecimal monthlyMaintenanceFee;
        BigDecimal minimumBalance;
        String secretKey;
        Date creationDate;

        //balance
        if (checkingAccountDTO.getBalance() == null) {
            throw new IllegalStateException("balance cannot be null");
        } else {
            balance = checkingAccountDTO.getBalance();
        }
        //Primary owner validation
        if (primaryOwner == null) {
            throw new IllegalStateException("Account must have an owner");
        }
        //monthlyMaintenanceFee
        monthlyMaintenanceFee = new BigDecimal("12");

        //minimumBalance
        minimumBalance=new BigDecimal("250");

        //secretKey
        if (checkingAccountDTO.getSecretKey() == null) {
            throw new IllegalStateException("Secret key cannot be null");
        } else {
            secretKey = checkingAccountDTO.getSecretKey();
        }
        //creation date
        creationDate = new Date();

        return new CheckingAccount(balance, primaryOwner, monthlyMaintenanceFee, minimumBalance, secretKey, creationDate, Status.ACTIVE);
    }


    public static boolean checkIfPrimaryOwnerIsLessThen24YO(CheckingAccount checkingAccount){
        Date today = new Date();
        Date dateOfBirth = checkingAccount.getPrimaryOwner().getDateOfBirth();
        int yearsOld = Period.between(dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears();
        return yearsOld<24;
    }
    public static StudentCheckingAccount convertToStudentCheckingAccount(CheckingAccount checkingAccount){
        if (checkingAccount.getSecondaryOwner()==null){
            return new StudentCheckingAccount(checkingAccount.getBalance(),checkingAccount.getPrimaryOwner(),checkingAccount.getSecretKey(),checkingAccount.getCreationDate(),checkingAccount.getStatus());
        }else{
            return new StudentCheckingAccount(checkingAccount.getBalance(),checkingAccount.getPrimaryOwner(),checkingAccount.getSecondaryOwner(),checkingAccount.getSecretKey(),checkingAccount.getCreationDate(),checkingAccount.getStatus());
        }
    }

}