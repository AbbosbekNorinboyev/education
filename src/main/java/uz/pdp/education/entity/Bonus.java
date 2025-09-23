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
@Table(name = "bonus")
public class Bonus extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser authUser;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private Months months;
}