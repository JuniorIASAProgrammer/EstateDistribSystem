package com.realestate.serviceuser.repo;

import com.realestate.serviceuser.repo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findOneById(long id);

    Optional<User> findOneByEmail(String email);
}
