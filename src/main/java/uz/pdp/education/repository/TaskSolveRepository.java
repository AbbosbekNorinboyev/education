package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.TaskSolve;

import java.util.List;

@Repository
public interface TaskSolveRepository extends JpaRepository<TaskSolve, Long> {
    List<TaskSolve> findAllByTaskId(Long id);

    @Query("select ts from TaskSolve ts where (?1 is null or ts.student.id=?1)" +
            "and (?2 is null or ts.teacher.id=?2) and (?3 is null or ts.task.lesson.id=?3)" +
            "and (?4 is null or ts.group.id=?4)")
    List<TaskSolve> filter(Long studentId, Long teacherId, Long lessonId, Long groupId);
}