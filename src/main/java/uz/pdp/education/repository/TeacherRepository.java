package uz.pdp.education.repository;

import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.Teacher;

@Repository
public interface TeacherRepository extends GenericRepository<Teacher, Long> {
}