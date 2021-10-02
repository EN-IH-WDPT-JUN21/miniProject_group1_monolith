package com.ironhack.midterm.bankingAPI.service.impl;

import com.ironhack.midterm.bankingAPI.builders.ThirdPartyBuilder;
import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import com.ironhack.midterm.bankingAPI.dto.ThirdPartyDTO;
import com.ironhack.midterm.bankingAPI.repository.roles.ThirdPartyRepository;
import com.ironhack.midterm.bankingAPI.repository.roles.UserRepository;
import com.ironhack.midterm.bankingAPI.service.interfaces.IThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ThirdPartyService implements IThirdPartyService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO) {
        if (userRepository.findByUsername(thirdPartyDTO.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already exists!");
        }else {
            return thirdPartyRepository.save(ThirdPartyBuilder.build(thirdPartyDTO));
        }

    }
}
