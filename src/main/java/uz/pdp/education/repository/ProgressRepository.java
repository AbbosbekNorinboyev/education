package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.Progress;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findAllByLessonIdAndUserId(Long lessonId, Long userId);

    List<Progress> findAllByLessonId(Long lessonId);
}