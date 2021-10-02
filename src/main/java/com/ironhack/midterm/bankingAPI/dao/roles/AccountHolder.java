package com.ironhack.midterm.bankingAPI.dao.roles;

import com.ironhack.midterm.bankingAPI.dao.other.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHolder extends User {
    @NotBlank(message = "Account holder must have a name")
    private String name;

    @NotNull(message = "Date of birth must be present")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @NotNull(message = "User has to have an address")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "mailing_address_id")
    private Address mailingAddress;

    public AccountHolder(String username, String password, String name, Date dateOfBirth, Address address) {
        super(username, password);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        Set<Role> role = new HashSet<>();
        role.add(new Role("ACCOUNT_HOLDER",this));
        super.setRoles(role);
    }

    public AccountHolder(String username, String password, String name, Date dateOfBirth, Address address, Address mailingAddress) {
        super(username, password);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mailingAddress = mailingAddress;
        Set<Role> role = new HashSet<>();
        role.add(new Role("ACCOUNT_HOLDER",this));
        super.setRoles(role);

    }
}
