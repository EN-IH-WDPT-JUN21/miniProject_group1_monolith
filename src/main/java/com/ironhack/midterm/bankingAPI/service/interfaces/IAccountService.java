package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountService {

    List<Account> findAllAccounts();
    Account findAccountById(Long id);
    List<Account> findAllUserAccountsByUsername(String username);
    Account updateBalanceById(Long id, BigDecimal balance);
}
