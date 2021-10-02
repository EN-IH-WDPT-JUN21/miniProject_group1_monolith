package com.ironhack.midterm.bankingAPI.controller.impl;

import com.ironhack.midterm.bankingAPI.controller.interfaces.IAccountController;
import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccountController implements IAccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/admin/account")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> findAllAccounts(){
        return accountService.findAllAccounts();
    }

    @GetMapping("/admin/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account findAccountById(@PathVariable("id") Long id){
        return accountService.findAccountById(id);
    }
    @GetMapping("/my_accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> findAllUserAccounts() {
        //Casting to userDetails, if we cast to Principal it gives casting error is the runtime, getPrincipal() method is confusing, it returns an object.
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return accountService.findAllUserAccountsByUsername(username);
    }

    @PutMapping("/admin/balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account updateBalanceById(@PathVariable("id") Long id,@RequestBody BigDecimal balance) {
        return accountService.updateBalanceById(id, balance);
    }
}
