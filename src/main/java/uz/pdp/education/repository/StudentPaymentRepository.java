package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.StudentPayment;

import java.util.List;

@Repository
public interface StudentPaymentRepository extends JpaRepository<StudentPayment, Long> {
    @Query(value = "SELECT * FROM student_payment as sp where sp.user_id = ?1" +
            " AND (?2 IS NULL OR sp.months = ?2)", nativeQuery = true)
    List<StudentPayment> findAllByUserIdAndMonth(Long studentId, String month);

    @Query(value = "select * from student_payment as s where s.group_id=?1", nativeQuery = true)
    List<StudentPayment> findAllByGroupId(Long groupId);

    @Query(value = "SELECT sp.months, SUM(sp.amount) FROM student_payment sp GROUP BY sp.months", nativeQuery = true)
    List<Object[]> findAllByMonthsNative();

    List<StudentPayment> findAllByUserId(Long id);
}