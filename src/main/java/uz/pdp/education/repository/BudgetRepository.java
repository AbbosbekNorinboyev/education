package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query(value = "select sum(b.amount) from budget b where b.budget_type = ?1", nativeQuery = true)
    Double findByBudgetType(String type);
}