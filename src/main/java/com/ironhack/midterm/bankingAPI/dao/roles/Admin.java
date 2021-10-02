package com.ironhack.midterm.bankingAPI.dao.roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {
    @NotBlank(message = "Admin has to have a name")
    private String name;

    public Admin(String name, String username, String password) {
        super(username, password);
        this.name = name;
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(new Role("ADMIN",this));
        super.setRoles(adminRole);
    }
}
