package uz.pdp.education.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.entity.Teacher;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentCreateDTO {
    @NotBlank(message = "fullName can not be null or empty")
    private String fullName;
    private int age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
