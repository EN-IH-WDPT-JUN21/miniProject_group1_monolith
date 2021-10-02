package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.builders.CheckingAccountBuilder;
import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.accounts.CheckingAccount;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.CheckingAccountDTO;
import com.ironhack.midterm.bankingAPI.repository.accounts.CheckingAccountRepository;
import com.ironhack.midterm.bankingAPI.repository.accounts.StudentCheckingAccountRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.AccountHolderRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.ICheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CheckingAccountService implements ICheckingAccountService {
    @Autowired
    CheckingAccountRepository checkingAccountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    StudentCheckingAccountRepository studentCheckingAccountRepository;

    public Account createChecking(CheckingAccountDTO checkingAccountDTO) {
        Optional<AccountHolder> primaryAccountHolder = accountHolderRepository.findById(checkingAccountDTO.getPrimaryOwnerId());
        if (primaryAccountHolder.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Primary owner not found!");
        }
        if (checkingAccountDTO.getSecondaryOwnerId()==null){
            CheckingAccount checkingAccount = CheckingAccountBuilder.build(checkingAccountDTO,primaryAccountHolder.get());
            if (CheckingAccountBuilder.checkIfPrimaryOwnerIsLessThen24YO(checkingAccount)){
                return studentCheckingAccountRepository.save(CheckingAccountBuilder.convertToStudentCheckingAccount(checkingAccount));
            }else{
                return checkingAccountRepository.save(checkingAccount);
            }
        }else{
            Optional<AccountHolder> secondaryAccount = accountHolderRepository.findById(checkingAccountDTO.getSecondaryOwnerId());
            if (secondaryAccount.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Secondary owner not found!");
            }
            else{
                CheckingAccount checkingAccount = CheckingAccountBuilder.build(checkingAccountDTO,primaryAccountHolder.get(),secondaryAccount.get());
                if (CheckingAccountBuilder.checkIfPrimaryOwnerIsLessThen24YO(checkingAccount)){
                    return studentCheckingAccountRepository.save(CheckingAccountBuilder.convertToStudentCheckingAccount(checkingAccount));
                }else{
                    return checkingAccountRepository.save(checkingAccount);
                }
            }
        }
    }
}
