package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.education.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}