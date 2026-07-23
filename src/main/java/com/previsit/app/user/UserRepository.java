package com.previsit.app.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByMobile(String mobile);

  boolean existsByEmail(String email);

  Optional<User> findByMobile(String mobile);

}