package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.LessonRequest;
import uz.pdp.education.dto.response.LessonResponse;
import uz.pdp.education.entity.Lesson;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonMapper {
    public Lesson toEntity(LessonRequest request) {
        return Lesson.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
    }

    public LessonResponse toResponse(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .groupId(lesson.getGroup() != null ? lesson.getGroup().getId() : null)
                .build();
    }

    public List<LessonResponse> responseList(List<Lesson> lessons) {
        if (lessons != null && !lessons.isEmpty()) {
            return lessons.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Lesson entity, LessonRequest request) {
        if (request == null) {
            return;
        }
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            entity.setTitle(request.getTitle());
        }
        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            entity.setDescription(request.getDescription());
        }
    }
}
