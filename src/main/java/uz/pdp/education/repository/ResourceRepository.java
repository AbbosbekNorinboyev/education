package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.Resources;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resources, Long> {
    List<Resources> findAllByLessonId(Long lessonId);
}