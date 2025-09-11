package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.education.enums.Weeks;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_day")
public class GroupDay extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Weeks weeks;

    private String groupTime;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups group;
}