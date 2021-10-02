package com.ironhack.midterm.bankingAPI.builders;

import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ThirdPartyBuilder {
    public static ThirdParty build(ThirdPartyDTO thirdPartyDTO){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return new ThirdParty(
                thirdPartyDTO.getUsername(),
                bCryptPasswordEncoder.encode(thirdPartyDTO.getPassword()),
                thirdPartyDTO.getName(),
                bCryptPasswordEncoder.encode(thirdPartyDTO.getKey()));
    }
}
