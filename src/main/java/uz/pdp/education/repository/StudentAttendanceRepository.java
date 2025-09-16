package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.StudentAttendance;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {
}