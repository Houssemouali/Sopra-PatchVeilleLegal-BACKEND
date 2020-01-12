package com.sopra.patch.dao.Repository;

import org.springframework.stereotype.Repository;

import com.sopra.patch.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    
}
