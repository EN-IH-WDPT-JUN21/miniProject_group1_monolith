package com.ironhack.midterm.bankingAPI.repository.other;

import com.ironhack.midterm.bankingAPI.dao.other.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
