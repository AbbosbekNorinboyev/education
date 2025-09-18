package uz.pdp.education.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.education.enums.BudgetType;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "budget")
public class Budget extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String description;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private BudgetType budgetType;
}