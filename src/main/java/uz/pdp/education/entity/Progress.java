package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.education.enums.ProgressStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "progress")
public class Progress extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    private Integer correctAnswer;
    private Integer totalQuestion;

    private Double percentage;
}