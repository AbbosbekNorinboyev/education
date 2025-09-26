package uz.pdp.education.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonMapper lessonMapper;
    private final GroupsRepository groupsRepository;
    private final LessonRepository lessonRepository;
    private final EntityManager entityManager;

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

    @Override
    public Response<?> filter(Long groupId, String title, String description) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lesson> criteriaQuery = criteriaBuilder.createQuery(Lesson.class);
        Root<Lesson> lessonRoot = criteriaQuery.from(Lesson.class);

        List<Predicate> predicates = new ArrayList<>();

        if (groupId != null) {
            predicates.add(criteriaBuilder.equal(lessonRoot.get("group").get("id"), groupId));
        }
        if (title != null && !title.isEmpty()) {
            predicates.add(criteriaBuilder.like(lessonRoot.get("title"), "%" + title + "%"));
        }
        if (description != null  && !description.isEmpty()) {
            predicates.add(criteriaBuilder.like(lessonRoot.get("description"), "%" + description + "%"));
        }

        criteriaQuery.select(lessonRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        List<Lesson> lessons = entityManager.createQuery(criteriaQuery).getResultList();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Group list successfully found filter by teacher and subject")
                .success(true)
                .data(lessonMapper.responseList(lessons))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
