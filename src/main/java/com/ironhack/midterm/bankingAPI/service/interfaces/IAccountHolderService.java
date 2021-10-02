package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.AccountHolderDTO;

public interface IAccountHolderService {
    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
}
