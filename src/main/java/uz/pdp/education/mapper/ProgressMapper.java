package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.ProgressRequest;
import uz.pdp.education.dto.response.ProgressResponse;
import uz.pdp.education.entity.Progress;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProgressMapper {
    public Progress toEntity(ProgressRequest request) {
        return Progress.builder()
                .status(request.getStatus())
                .correctAnswer(request.getCorrectAnswer())
                .totalQuestion(request.getTotalQuestion())
                .percentage(request.getPercentage())
                .build();
    }

    public ProgressResponse toResponse(Progress progress) {
        return ProgressResponse.builder()
                .id(progress.getId())
                .userId(progress.getUser() != null ? progress.getUser().getId() : null)
                .lessonId(progress.getLesson() != null ? progress.getLesson().getId() : null)
                .status(progress.getStatus())
                .correctAnswer(progress.getCorrectAnswer())
                .totalQuestion(progress.getTotalQuestion())
                .percentage(progress.getPercentage())
                .build();
    }

    public List<ProgressResponse> responseList(List<Progress> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }
}