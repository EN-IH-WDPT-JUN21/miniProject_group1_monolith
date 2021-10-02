package com.ironhack.midterm.bankingAPI.controller.interfaces;

import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dto.SavingAccountDTO;

public interface ISavingAccountController {
    public SavingAccount createSavingAccount(SavingAccountDTO savingAccountDTO);
}
