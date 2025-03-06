package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.education.entity.AuthUser;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Integer> {
    Optional<AuthUser> findByUsername(String username);
}