package com.ironhack.midterm.bankingAPI.controller.impl;

import com.ironhack.midterm.bankingAPI.controller.interfaces.ICheckingAccountController;
import com.ironhack.midterm.bankingAPI.dao.accounts.Account;
import com.ironhack.midterm.bankingAPI.dto.CheckingAccountDTO;
import com.ironhack.midterm.bankingAPI.service.impl.CheckingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CheckingAccountController implements ICheckingAccountController {
    @Autowired
    CheckingAccountService checkingAccountService;

    @PostMapping("/admin/create_checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createChecking(@RequestBody CheckingAccountDTO checkingAccountDTO) {
        return checkingAccountService.createChecking(checkingAccountDTO);
    }
}
