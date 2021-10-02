package com.ironhack.midterm.bankingAPI.controller.interfaces;

import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.AccountHolderDTO;

public interface IAccountHolderController {
    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
}
