package uz.pdp.education.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.education.enums.ProgressStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressRequest {
    private Long userId;
    private Long lessonId;
    private ProgressStatus status;
    private Integer correctAnswer;
    private Integer totalQuestion;
    private Double percentage;
}