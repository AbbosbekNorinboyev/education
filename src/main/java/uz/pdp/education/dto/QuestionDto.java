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
public class QuestionDto {
    private Long id;
    private String text;
    private Long lessonId;
    private List<OptionsDto> options;

    @Data
    @Builder
    public static class QuestionCreateDto {
        private String text;
        private List<OptionsDto.OptionsCreteDto> options;
    }
}
