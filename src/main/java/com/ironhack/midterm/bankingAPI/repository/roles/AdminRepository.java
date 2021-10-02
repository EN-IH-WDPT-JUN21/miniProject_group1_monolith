package com.ironhack.midterm.bankingAPI.repository.roles;

import com.ironhack.midterm.bankingAPI.dao.roles.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
