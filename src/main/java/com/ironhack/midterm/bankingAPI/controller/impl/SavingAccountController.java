package com.ironhack.midterm.bankingAPI.controller.impl;

import com.ironhack.midterm.bankingAPI.controller.interfaces.ISavingAccountController;
import com.ironhack.midterm.bankingAPI.dao.accounts.SavingAccount;
import com.ironhack.midterm.bankingAPI.dto.SavingAccountDTO;
import com.ironhack.midterm.bankingAPI.service.impl.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class SavingAccountController implements ISavingAccountController  {
    @Autowired
    SavingAccountService savingAccountService;

    @PostMapping("/admin/create_savings")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingAccount createSavingAccount(@RequestBody @Valid SavingAccountDTO savingAccountDTO) {
        return savingAccountService.save(savingAccountDTO);
    }
}
