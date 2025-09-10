package uz.pdp.education.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
}
