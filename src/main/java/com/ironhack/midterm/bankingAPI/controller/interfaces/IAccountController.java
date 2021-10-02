package com.ironhack.midterm.bankingAPI.controller.interfaces;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountController {
    List<Account> findAllAccounts();
    Account findAccountById(Long id);
    List<Account> findAllUserAccounts();
    Account updateBalanceById(Long id, BigDecimal balance);
}
