package com.ironhack.midterm.bankingAPI.service.interfaces;

import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyDTO;

public interface IThirdPartyService {
    ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO);
}
