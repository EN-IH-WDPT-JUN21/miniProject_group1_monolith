package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dao.roles.User;
import com.ironhack.midterm.bankingAPI.dto.BalanceDTO;
import com.ironhack.midterm.bankingAPI.repository.accounts.AccountRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.UserRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class AccountService implements IAccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }
    public Account findAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()){
            throw new ResponseStatusException(BAD_REQUEST,"Account not found!");
        }
        else{
            return account.get();
        }
    }

    public List<Account> findAllUserAccountsByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        //User always should be present, because we're logged in with this user
        Long userId = user.get().getId();
        List<Account> allAccounts = accountRepository.findAll();
        List<Account> userAccounts = new ArrayList<>();
        for (Account account : allAccounts){
            //
            if (account.getPrimaryOwner().getId().equals(userId)){
                userAccounts.add(account);
            }
            //separated because we need to check if the 2nd owner is initialized, otherwise we get a null pointer exception
            else if(account.getSecondaryOwner()!=null && account.getSecondaryOwner().getId().equals(userId)){
                userAccounts.add(account);
            }
        }
            return userAccounts;
    }
    public Account updateBalanceById(Long id, BalanceDTO balanceDTO) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()){
            throw new ResponseStatusException(BAD_REQUEST,"Account not found!");
        }else{
            account.get().setBalance(balanceDTO.getBalance());
            return accountRepository.save(account.get());
        }
    }

    public Account activateAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()){
            throw new ResponseStatusException(BAD_REQUEST,"Account not found!");
        }else{
            account.get().activateAccount();
            return accountRepository.save(account.get());
        }
    }
}
