package com.ironhack.midterm.bankingAPI.controller.interfaces;

import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dto.CheckingAccountDTO;

public interface ICheckingAccountController {
    Account createChecking(CheckingAccountDTO checkingAccountDTO);
}
