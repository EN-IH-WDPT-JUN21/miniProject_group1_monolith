package com.ironhack.midterm.bankingAPI.repository.roles;

import com.ironhack.midterm.bankingAPI.dao.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
