package com.ironhack.midterm.bankingAPI.controller.impl;

import com.ironhack.midterm.bankingAPI.controller.interfaces.ICreditCardController;
import com.ironhack.midterm.bankingAPI.dao.accounts.CreditCard;
import com.ironhack.midterm.bankingAPI.dto.CreditCardDTO;
import com.ironhack.midterm.bankingAPI.service.impl.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CreditCardController implements ICreditCardController {
    @Autowired
    CreditCardService creditCardService;

    @PostMapping("/admin/create_cc")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard saveCC(@RequestBody CreditCardDTO creditCardDTO) {
        return creditCardService.saveCC(creditCardDTO);
    }
}
