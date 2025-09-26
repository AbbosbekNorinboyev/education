package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.dto.DiscountMonthDto;
import uz.pdp.education.entity.Discount;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Query(value = "select * from discount d where d.group_id = ?1 and " +
            "(?2 is null or d.months = ?2)", nativeQuery = true)
    List<Discount> findAllByGroupIdAndMonths(Long groupId, String months);

    List<Discount> findAllByStudentId(Long studentId);

    @Query(value = "select d.months, sum(d.amount) from discount d group by d.months",nativeQuery = true)
    List<DiscountMonthDto> findAllByMonths();
}