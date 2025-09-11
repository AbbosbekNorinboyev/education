package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "about_me")
public class AboutMe extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String title;

    // TEACHER
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private AuthUser teacher;
}