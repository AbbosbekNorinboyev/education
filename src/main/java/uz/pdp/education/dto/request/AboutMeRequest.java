package uz.pdp.education.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutMeRequest {
    @NotBlank(message = "title can not be null or empty")
    private String title;
    // TEACHER
    private Long teacherId;
}