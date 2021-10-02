package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.builders.SavingAccountBuilder;
import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.SavingAccountDTO;
import com.ironhack.midterm.bankingAPI.repository.accounts.SavingAccountRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.AccountHolderRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.ISavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class SavingAccountService implements ISavingAccountService {
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    SavingAccountRepository savingAccountRepository;

    public SavingAccount save(SavingAccountDTO savingAccountDTO) {
        Optional<AccountHolder> primaryAccountHolder = accountHolderRepository.findById(savingAccountDTO.getPrimaryOwnerId());
        if (primaryAccountHolder.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Primary owner not found!");
        }
        if (savingAccountDTO.getSecondaryOwnerId()==null){
            return savingAccountRepository.save(SavingAccountBuilder.build(savingAccountDTO,primaryAccountHolder.get()));
        }else{
            Optional<AccountHolder> secondaryAccount = accountHolderRepository.findById(savingAccountDTO.getSecondaryOwnerId());
            if (secondaryAccount.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Secondary owner not found!");
            }
            else{
                return savingAccountRepository.save(SavingAccountBuilder.build(savingAccountDTO,primaryAccountHolder.get(),secondaryAccount.get()));
            }
        }
    }
}
