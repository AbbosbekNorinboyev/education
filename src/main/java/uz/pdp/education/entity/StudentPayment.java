package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.education.enums.Months;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "student_payment")
public class StudentPayment extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Months months;

    private Integer year;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;
}
