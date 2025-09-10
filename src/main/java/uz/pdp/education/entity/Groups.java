package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.education.enums.GroupStatus;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "groups")
public class Groups extends BaseEntity {
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private AuthUser teacher;

    private Double price;

    // oquvchilar
    @ManyToMany
    @JoinTable(
            name = "group_student",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<AuthUser> students = new HashSet<>();

    // yordamchi oqituvchilar
    @ManyToMany
    @JoinTable(
            name = "group_support",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "support_id")
    )
    private Set<AuthUser> supports = new HashSet<>();

    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private GroupStatus status;
}
