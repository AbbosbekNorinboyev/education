package uz.pdp.education.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDto {
    @NotBlank(message = "fullName can not be null or empty")
    private String fullName;
    private int age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
