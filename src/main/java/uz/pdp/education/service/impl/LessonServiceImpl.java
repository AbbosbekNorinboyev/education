package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.request.LessonRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.entity.Lesson;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.LessonMapper;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.repository.LessonRepository;
import uz.pdp.education.service.LessonService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonMapper lessonMapper;
    private final GroupsRepository groupsRepository;
    private final LessonRepository lessonRepository;

    @Override
    public Response<?> createLesson(LessonRequest request) {
        Groups group = groupsRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + request.getGroupId()));
        Lesson lesson = lessonMapper.toEntity(request);
        lesson.setGroup(group);
        lessonRepository.save(lesson);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Lesson successfully created")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Lesson successfully found")
                .success(true)
                .data(lessonMapper.toResponse(lesson))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllLesson() {
        List<Lesson> lessons = lessonRepository.findAll();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Lesson list successfully found")
                .success(true)
                .data(lessonMapper.responseList(lessons))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> updateLesson(LessonRequest request, Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + id));
        lessonMapper.update(lesson, request);
        lessonRepository.save(lesson);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Lesson successfully update")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> deleteLesson(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + id));
        lessonRepository.delete(lesson);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Lesson successfully deleted")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getLessonsByGroupId(Long groupId) {
        Groups group = groupsRepository.save(groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId)));
        List<Lesson> lessons = lessonRepository.findAllByGroupId(group.getId());
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Lesson list successfully found by group id")
                .success(true)
                .data(lessonMapper.responseList(lessons))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
