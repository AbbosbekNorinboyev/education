package uz.pdp.education.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.education.enums.ProgressStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgressResponse {
    private Long id;
    private Long userId;
    private Long lessonId;
    private ProgressStatus status;
    private Integer correctAnswer;
    private Integer totalQuestion;
    private Double percentage;
}