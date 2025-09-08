package uz.pdp.education.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.education.entity.Teacher;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubjectDto {
    private Long id;
    @NotBlank(message = "name can not be null or empty")
    private String name;
    private Double price;
    private Set<Teacher> teachers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
}
