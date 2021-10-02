package com.ironhack.midterm.bankingAPI.controller.interfaces;

import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyDTO;

public interface IThirdPartyController {
    ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO);
}
