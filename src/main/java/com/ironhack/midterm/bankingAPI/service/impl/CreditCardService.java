package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.builders.CreditCardBuilder;
import com.ironhack.midterm.bankingAPI.dao.accounts.CreditCard;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.CreditCardDTO;
import com.ironhack.midterm.bankingAPI.repository.accounts.CreditCardRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.AccountHolderRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CreditCardService implements ICreditCardService {
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;


    public CreditCard saveCC(CreditCardDTO creditCardDTO) {
        Optional<AccountHolder> primaryAccountHolder = accountHolderRepository.findById(creditCardDTO.getPrimaryOwnerId());
        if (primaryAccountHolder.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Primary owner not found!");
        }
        if (creditCardDTO.getSecondaryOwnerId()==null){
            return creditCardRepository.save(CreditCardBuilder.build(creditCardDTO,primaryAccountHolder.get()));
        }else{
            Optional<AccountHolder> secondaryAccount = accountHolderRepository.findById(creditCardDTO.getSecondaryOwnerId());
            if (secondaryAccount.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Secondary owner not found!");
            }
            else{
                return creditCardRepository.save(CreditCardBuilder.build(creditCardDTO,primaryAccountHolder.get(),secondaryAccount.get()));
            }
        }
    }
}
