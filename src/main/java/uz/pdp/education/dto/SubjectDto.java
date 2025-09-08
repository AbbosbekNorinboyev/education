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
public class SubjectDto {
    @NotBlank(message = "name can not be null or empty")
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
