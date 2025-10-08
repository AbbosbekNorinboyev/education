package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.ResultTestDto;
import uz.pdp.education.entity.TestResult;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestResultMapper {
    private ResultTestDto toDto(TestResult result) {
        return ResultTestDto.builder()
                .incorrectCount(result.getTotalIncorrect())
                .attemptCount(result.getAttemptCount())
                .correctCount(result.getTotalCorrect())
                .studentId(result.getStudent() != null ? result.getStudent().getId() : null)
                .lessonId(result.getLessonId())
                .build();
    }

    public List<ResultTestDto> dtoList(List<TestResult> results) {
        if (results != null && !results.isEmpty()) {
            return results.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }
}