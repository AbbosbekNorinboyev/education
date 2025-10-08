package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task")
public class Task extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String fileUrl;
    @Column(columnDefinition = "TEXT")
    private String video;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}