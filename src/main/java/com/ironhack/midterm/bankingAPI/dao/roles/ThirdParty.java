package com.ironhack.midterm.bankingAPI.dao.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ThirdParty extends User{

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @JsonIgnore
    @NotNull(message = "Hash Key cannot be null")
    private String hashKey;

    public ThirdParty(String username, String password, String name, String hashKey) {
        super(username, password);
        this.name = name;
        this.hashKey = hashKey;
        Set<Role> role = new HashSet<>();
        role.add(new Role("THIRD_PARTY",this));
        super.setRoles(role);
    }
}
