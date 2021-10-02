package com.ironhack.midterm.bankingAPI.repository.roles;

import com.ironhack.midterm.bankingAPI.dao.roles.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//Repository annotation is not needed when extending JPARepository interface
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
