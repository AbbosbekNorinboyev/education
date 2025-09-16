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
@Table(name = "room")
public class Room extends BaseEntity {
    private String name;
    private Integer number;
    private String description;
    private Integer capacity;
}