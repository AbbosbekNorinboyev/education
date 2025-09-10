package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends GenericRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    @Query(value = "select * from auth_user where role = ?1", nativeQuery = true)
    List<AuthUser> findAllByRole(String role);

}