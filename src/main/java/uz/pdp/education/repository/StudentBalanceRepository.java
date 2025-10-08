package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.StudentBalance;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentBalanceRepository extends JpaRepository<StudentBalance, Long> {
    @Query(value = "select * from student_balance as s where s.group_id=?1 and s.user_id=?2", nativeQuery = true)
    Optional<StudentBalance> findAllByGroupIdAndStudentId(Long groupId, Long studentId);

    List<StudentBalance> findAllByGroupId(Long groupId);

    List<StudentBalance> findAllByUserId(Long userId);
}