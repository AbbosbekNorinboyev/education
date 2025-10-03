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
@Table(name = "options")
public class Options extends BaseEntity {
    private String option;
    private Boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}