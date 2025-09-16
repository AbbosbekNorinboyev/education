package uz.pdp.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teacher_attendance")
public class TeacherAttendance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private AuthUser teacher;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    private Boolean active;
}