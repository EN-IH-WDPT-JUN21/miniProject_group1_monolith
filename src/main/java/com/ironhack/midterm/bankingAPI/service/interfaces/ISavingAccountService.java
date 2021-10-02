package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dto.SavingAccountDTO;

public interface ISavingAccountService {
    SavingAccount save(SavingAccountDTO savingAccountDTO);
}
