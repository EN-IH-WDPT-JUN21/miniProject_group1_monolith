package com.ironhack.midterm.bankingAPI.repository.roles;

import com.ironhack.midterm.bankingAPI.dao.roles.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThirdPartyRepository extends JpaRepository<ThirdParty,Long> {
}
