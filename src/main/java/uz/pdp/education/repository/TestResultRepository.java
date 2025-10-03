package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.TestResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    Optional<TestResult> findByStudentAndLessonId(AuthUser student, Long lessonId);

    @Query(value = "select distinct (ts.*)\n" +
            "from test_results as ts\n" +
            "         join lesson as l on l.id = ts.lesson_id\n" +
            "         join groups as g on g.id = l.group_id\n" +
            "where (?1 is null or ts.student_id =?1)\n" +
            "  and (?2 is null or g.teacher_id =?2)\n" +
            "  and (?3 is null or ts.lesson_id =?3)\n" +
            "  and (?4 is null or g.id =?4)", nativeQuery = true)
    List<TestResult> filter(Long studentId, Long teacherId, Long lessonId, Long groupId);
}