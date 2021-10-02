package com.ironhack.midterm.bankingAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderDTO {
    @NotNull(message = "username must be passed!")
    private String username;
    @NotNull(message = "password must be passed!")
    private String password;
    @NotNull(message = "name must be passed!")
    private String name;
    @NotNull(message = "date of birth must be passed!")
    private Date dateOfBirth;
    @NotNull(message = "city must be passed!")
    private String city;
    @NotNull(message = "postal code must be passed!")
    private String postalCode;
    @NotNull(message = "country must be passed!")
    private String country;
    @NotNull(message = "street must be passed!")
    private String street;
    @NotNull(message = "house number must be passed!")
    private String houseNumber;
    private String mailingCity;
    private String mailingPostalCode;
    private String mailingCountry;
    private String mailingStreet;
    private String mailingHouseNumber;

    public AccountHolderDTO(String username, String password, String name, Date dateOfBirth, String city, String postalCode, String country, String street, String houseNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.street = street;
        this.houseNumber = houseNumber;
    }
}

