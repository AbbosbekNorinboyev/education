package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.education.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Query(value = "delete from subject s where s.id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteSubjectByTeacherId(Integer id);
}