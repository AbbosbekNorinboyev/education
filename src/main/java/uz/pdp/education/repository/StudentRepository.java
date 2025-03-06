package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.education.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "delete from student s where s.id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteStudentByTeacherId(Integer id);
}