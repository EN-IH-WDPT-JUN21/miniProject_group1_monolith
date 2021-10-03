package com.ironhack.midterm.bankingAPI.controller.impl;

import com.ironhack.midterm.bankingAPI.controller.interfaces.IThirdPartyController;
import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyDTO;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyTransactionDTO;
import com.ironhack.midterm.bankingAPI.dto.TransactionConfirmationDTO;
import com.ironhack.midterm.bankingAPI.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ThirdPartyController implements IThirdPartyController {
    @Autowired
    IThirdPartyService thirdPartyService;

    @PostMapping("/admin/create_third_party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        return thirdPartyService.createThirdParty(thirdPartyDTO);
    }
    @PostMapping("/third_party/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TransactionConfirmationDTO sendFunds(@RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO, @RequestHeader String hashedKey) {
        return thirdPartyService.sendFunds(thirdPartyTransactionDTO,hashedKey);
    }
    @PostMapping("/third_party/receive")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TransactionConfirmationDTO receiveFunds(@RequestBody ThirdPartyTransactionDTO thirdPartyTransactionDTO, @RequestHeader String hashedKey) {
        return thirdPartyService.receiveFunds(thirdPartyTransactionDTO,hashedKey);
    }
}
