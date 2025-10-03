package uz.pdp.education.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultTestDto {
    private Long attemptCount;
    private Long correctCount;
    private Long incorrectCount;
    private Long studentId;
    private Long lessonId;
    private List<OptionsDto> correctOptionIds;
}
