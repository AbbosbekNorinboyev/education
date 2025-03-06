package uz.pdp.education.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubjectCreateDTO {
    @NotBlank(message = "name can not be null or empty")
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
