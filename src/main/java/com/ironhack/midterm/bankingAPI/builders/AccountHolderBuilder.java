package com.ironhack.midterm.bankingAPI.builders;

import com.ironhack.midterm.bankingAPI.dao.other.Address;
import com.ironhack.midterm.bankingAPI.dao.roles.AccountHolder;
import com.ironhack.midterm.bankingAPI.dto.AccountHolderDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AccountHolderBuilder {
    public static AccountHolder build(AccountHolderDTO accountHolderDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Address address = new Address(accountHolderDTO.getCity(), accountHolderDTO.getPostalCode(), accountHolderDTO.getStreet(), accountHolderDTO.getHouseNumber(), accountHolderDTO.getCountry());

        if(
            accountHolderDTO.getMailingCity() != null &&
            accountHolderDTO.getPostalCode() != null &&
            accountHolderDTO.getMailingStreet() != null &&
            accountHolderDTO.getMailingHouseNumber() != null &&
            accountHolderDTO.getMailingCountry() != null
        ) {
            Address mailingAddress = new Address(
                accountHolderDTO.getMailingCity(),
                accountHolderDTO.getPostalCode(),
                accountHolderDTO.getMailingStreet(),
                accountHolderDTO.getMailingHouseNumber(),
                accountHolderDTO.getMailingCountry()
            );
            return new AccountHolder(
                    accountHolderDTO.getUsername(),
                    bCryptPasswordEncoder.encode(accountHolderDTO.getPassword()),
                    accountHolderDTO.getName(),
                    accountHolderDTO.getDateOfBirth(),
                    address,
                    mailingAddress
                    );
        }else{
            return new AccountHolder(
                    accountHolderDTO.getUsername(),
                    bCryptPasswordEncoder.encode(accountHolderDTO.getPassword()),
                    accountHolderDTO.getName(),
                    accountHolderDTO.getDateOfBirth(),
                    address
            );
        }
    }
}
