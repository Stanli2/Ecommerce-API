package com.ike.ecommerce.model.dao;

import com.ike.ecommerce.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocalUserDao extends JpaRepository<LocalUser, Long> {

    Optional<LocalUser> findByUsernameContainingIgnoreCase(String username);

    Optional<LocalUser> findByEmailContainingIgnoreCase(String email);
}
