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
public class GroupDto {
    @NotBlank(message = "name can be null or empty")
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
