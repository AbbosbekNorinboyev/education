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
@Table(name = "discount")
public class Discount extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private AuthUser student;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Months months;
    private String reason;
}