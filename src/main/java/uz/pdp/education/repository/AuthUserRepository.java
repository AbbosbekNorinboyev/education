package uz.pdp.education.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.AuthUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthUserRepository extends GenericRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    @Query("select count(*), au.role from AuthUser as au group by au.role")
    List<Tuple> roleStatistics();

    @Query(value = "select * from auth_user au" +
            "         left join group_student gs on au.id = gs.student_id" +
            " where gs.group_id = ?1",
            nativeQuery = true)
    List<AuthUser> findAllByGroupId(Long groupId);
}