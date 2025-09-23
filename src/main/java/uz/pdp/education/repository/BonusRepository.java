package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.Bonus;
import uz.pdp.education.enums.Months;

import java.util.List;
import java.util.Objects;

@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long> {
    List<Bonus> findAllByAuthUserId(Long userId);

    @Query(value = "select * from bonus as b where b.months = ?1", nativeQuery = true)
    List<Bonus> findAllByMonths(String month);

    @Query(value = "select b.months, sum(b.balance) from bonus as b group by b.months", nativeQuery = true)
    List<Object[]> findAllByMonthsNative();
}