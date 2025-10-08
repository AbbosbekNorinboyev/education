package uz.pdp.education.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.education.dto.AnswerDto;
import uz.pdp.education.dto.LessonDto;
import uz.pdp.education.dto.ResourcesDto;
import uz.pdp.education.dto.VideoDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.*;
import uz.pdp.education.enums.ProgressStatus;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.LessonMapper;
import uz.pdp.education.mapper.ProgressMapper;
import uz.pdp.education.mapper.ResourceMapper;
import uz.pdp.education.mapper.VideoMapper;
import uz.pdp.education.repository.*;
import uz.pdp.education.service.LessonService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonMapper lessonMapper;
    private final GroupsRepository groupsRepository;
    private final LessonRepository lessonRepository;
    private final EntityManager entityManager;
    private final AuthUserRepository authUserRepository;
    private final ProgressRepository progressRepository;
    private final ProgressMapper progressMapper;
    private final QuestionRepository questionRepository;
    private final OptionsRepository optionsRepository;
    private final VideoMapper videoMapper;
    private final VideoRepository videoRepository;
    private final ResourceMapper resourceMapper;
    private final ResourceRepository resourceRepository;
    private final TaskRepository taskRepository;

    @Override
    public Response<?> createLesson(LessonDto.LessonCreteDto dto) {
        Groups group = groupsRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + dto.getGroupId()));
        Lesson lesson = lessonMapper.toEntity(dto);
        lesson.setGroup(group);
        lessonRepository.save(lesson);

        List<MultipartFile> resources = dto.getResources();
        List<MultipartFile> videos = dto.getVideos();

        try {
            if (videos != null && !videos.isEmpty()) {
                for (MultipartFile file : videos) {
                    if (file != null && !file.isEmpty()) {
                        byte[] bytes = file.getBytes();
                        String imageUrl = Base64.getEncoder().encodeToString(bytes);
                        VideoDto.VideoCreteDto creteDto = new VideoDto.VideoCreteDto(file);
                        Video video = videoMapper.toEntity(creteDto);
                        video.setUrl(imageUrl);
                        video.setLesson(lesson);

                        videoRepository.save(video);
                    }
                }
            }
            if (resources != null && !resources.isEmpty()) {
                for (MultipartFile file : resources) {
                    if (file != null && !file.isEmpty()) {
                        ResourcesDto.ResourcesCreteDto creteDto = new ResourcesDto.ResourcesCreteDto(
                                file.getContentType(), file
                        );
                        byte[] bytes = file.getBytes();
                        String imageUrl = Base64.getEncoder().encodeToString(bytes);
                        Resources resource = resourceMapper.toEntity(creteDto);
                        resource.setUrl(imageUrl);
                        resource.setLesson(lesson);

                        resourceRepository.save(resource);
                    }
                }
            }
            Task task = new Task();
            task.setDescription(dto.getTaskDescription());
            task.setTitle(dto.getTaskTitle());
            task.setLesson(lesson);
            if (dto.getTaskFile() != null && !dto.getTaskFile().isEmpty()) {
                byte[] bytes = dto.getTaskFile().getBytes();
                String fileUrl = Base64.getEncoder().encodeToString(bytes);
                task.setFileUrl(fileUrl);
            }
            if (dto.getTaskVideo() != null && !dto.getTaskVideo().isEmpty()) {
                byte[] bytes = dto.getTaskVideo().getBytes();
                String video = Base64.getEncoder().encodeToString(bytes);
                task.setVideo(video);
            }
            taskRepository.save(task);
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Lesson successfully created")
                    .success(true)
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        } catch (Exception e) {
            return Response.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(e.getMessage())
                    .success(false)
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
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
    public Response<?> getAllLesson(Pageable pageable) {
        List<Lesson> lessons = lessonRepository.findAll(pageable).getContent();
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
    public Response<?> updateLesson(LessonDto.LessonCreteDto request, Long id) {
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
    public Response<?> submit(AuthUser user, Long lessonId, AnswerDto answerDto) {
        Progress progress = new Progress();
        int correctAnswer = 0;
        int submitCount = 0;
        double percentage = 0;
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        List<Progress> progressList = progressRepository.findAllByLessonIdAndUserId(lessonId, user.getId());
        if (progressList != null && !progressList.isEmpty()) {
            if (progressList.size() >= 3) {
                return Response.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST)
                        .message("SIZDA URINISHLAR SONI TUGAGAN")
                        .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                        .build();
            }
            submitCount = progressList.size() + 1;
        }
        progress.setUser(user);
        progress.setLesson(lesson);
        List<AnswerDto.ShortAnswer> answerList = answerDto.getAnswerList();
        if (answerList != null && !answerList.isEmpty()) {
            for (AnswerDto.ShortAnswer shortAnswer : answerList) {
                Question question = questionRepository.findById(shortAnswer.getQuestionId()).orElse(null);
                if (question != null) {
                    Options options = optionsRepository.findById(shortAnswer.getOptionId()).orElse(null);
                    if (options != null) {
                        if (options.getIsCorrect()) {
                            correctAnswer++;
                        }
                    }
                }
            }
            percentage = ((double) correctAnswer / answerDto.getTotalQuestion()) * 100;
            progress.setPercentage(percentage);
            progress.setCorrectAnswer(correctAnswer);
            progress.setTotalQuestion(answerDto.getTotalQuestion());
            if (percentage < 60) {
                progress.setStatus(ProgressStatus.BAD);
            } else if (percentage >= 60 && percentage < 80) {
                progress.setStatus(ProgressStatus.GOOD);
            } else {
                progress.setStatus(ProgressStatus.EXCELLENT);
            }
        }
        progressRepository.save(progress);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Successfully submit")
                .data(submitCount)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllProgressByLessonIdAndUserId(Long lessonId, Long userId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
        AuthUser authUser = authUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Progress> list = progressRepository.findAllByLessonIdAndUserId(lesson.getId(), authUser.getId());
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Progress list")
                .data(progressMapper.responseList(list))
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
        if (description != null && !description.isEmpty()) {
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
