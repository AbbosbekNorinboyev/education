package uz.pdp.education.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "support_teacher")
public class SupportTeacher extends BaseEntity {
    private String fullName;
    private String phoneNumber;
}