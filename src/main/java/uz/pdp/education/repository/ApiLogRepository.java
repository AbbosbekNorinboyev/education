package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.ApiLog;

@Repository
public interface ApiLogRepository extends JpaRepository<ApiLog, Long> {
}