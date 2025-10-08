package uz.pdp.education.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.education.dto.LessonDto;
import uz.pdp.education.entity.Lesson;
import uz.pdp.education.entity.Resources;
import uz.pdp.education.entity.Video;
import uz.pdp.education.repository.ResourceRepository;
import uz.pdp.education.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LessonMapper {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final VideoMapper videoMapper;
    private final VideoRepository videoRepository;

    public Lesson toEntity(LessonDto.LessonCreteDto request) {
        return Lesson.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
    }

    public LessonDto toResponse(Lesson lesson) {
        List<Resources> resources = resourceRepository.findAllByLessonId(lesson.getId());
        List<Video> videos = videoRepository.findAllByLessonId(lesson.getId());
        return LessonDto.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .groupId(lesson.getGroup() != null ? lesson.getGroup().getId() : null)
                .resources(resourceMapper.dtoList(resources))
                .videos(videoMapper.dtoList(videos))
                .build();
    }

    public List<LessonDto> responseList(List<Lesson> lessons) {
        if (lessons != null && !lessons.isEmpty()) {
            return lessons.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Lesson entity, LessonDto.LessonCreteDto request) {
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
