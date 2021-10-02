package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dto.CheckingAccountDTO;

public interface ICheckingAccountService {
    Account createChecking(CheckingAccountDTO checkingAccountDTO);
}
