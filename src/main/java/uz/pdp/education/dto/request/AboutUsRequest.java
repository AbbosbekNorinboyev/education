package uz.pdp.education.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutUsRequest {
    @NotBlank(message = "name can not be null or empty")
    private String name;
    private String title;
    private String description;
}