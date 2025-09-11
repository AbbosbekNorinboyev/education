package uz.pdp.education.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutMeResponse {
    private Long id;
    private String title;
    // TEACHER
    private Long teacherId;
}