package uz.pdp.education.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.education.enums.Role;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeacherDto {
    private Long id;
    @NotBlank(message = "fullName can not be null or empty")
    private String fullName;
    private int age;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
}
