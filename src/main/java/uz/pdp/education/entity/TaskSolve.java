package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_solve")
public class TaskSolve extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private AuthUser teacher;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private AuthUser student;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    private Double score;
}
