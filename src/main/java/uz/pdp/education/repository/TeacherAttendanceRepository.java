package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.TeacherAttendance;

@Repository
public interface TeacherAttendanceRepository extends JpaRepository<TeacherAttendance, Long> {
}