package uz.pdp.education.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionsDto {
    private Long id;
    private String option;
    private Boolean isCorrect;
    private Long questionId;

    @Data
    @Builder
    public static class OptionsCreteDto {
        private String option;
        private Boolean isCorrect;
    }
}