package ood.usedbookstore.repositories;

import jakarta.validation.constraints.Email;
import ood.usedbookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long Id);

    Optional<User> findByEmail(@Email String email);

    boolean existsById(Long Id);

    boolean existsByEmail(@Email String email);

    Optional<User> findBySuid(String suid);
}
