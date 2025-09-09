package uz.pdp.education.repository;

import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.AuthUser;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends GenericRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);
}