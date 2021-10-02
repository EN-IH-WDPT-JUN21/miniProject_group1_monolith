package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.builders.AccountHolderBuilder;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.AccountHolderDTO;
import com.ironhack.midterm.bankingAPI.repository.other.AddressRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.AccountHolderRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.UserRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountHolderService implements IAccountHolderService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AddressRepository addressRepository;

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        if (userRepository.findByUsername(accountHolderDTO.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already exists!");
        }
        else{

            AccountHolder accountHolder = AccountHolderBuilder.build(accountHolderDTO);
            addressRepository.save(accountHolder.getAddress());
            if (accountHolder.getMailingAddress()!=null){
                addressRepository.save(accountHolder.getMailingAddress());
            }
            return accountHolderRepository.save(accountHolder);
        }
    }
}
