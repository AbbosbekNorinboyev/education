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
public class AnswerDto {
    private Integer totalQuestion;
    private List<ShortAnswer> answerList;

    @Data
    public static class ShortAnswer {
        private Long questionId;
        private Long optionId;
    }
}