package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "test_results")
public class TestResult extends BaseEntity {
    @Column(name = "lesson_id")
    private Long lessonId;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private AuthUser student;
    @Column(name = "attempt_count")
    private Long attemptCount;
    @Column(name = "total_correct")
    private Long totalCorrect;
    @Column(name = "total_incorrect")
    private Long totalIncorrect;
}
