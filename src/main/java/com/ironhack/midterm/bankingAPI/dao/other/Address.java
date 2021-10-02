package com.ironhack.midterm.bankingAPI.dao.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "City cannot be empty")
    private String city;
    @NotNull(message = "Postal code cannot be empty")
    private String postalCode;
    @NotNull(message = "Name of the street cannot be empty")
    private String streetName;
    @NotNull(message = "House number cannot be empty")
    private String houseNumber;
    @NotNull(message = "Country cannot be empty")
    private String country;

    public Address(String city, String postalCode, String streetName, String houseNumber, String country) {
        this.city = city;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.country = country;
    }
}
