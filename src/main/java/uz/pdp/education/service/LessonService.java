package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.AnswerDto;
import uz.pdp.education.dto.LessonDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;

public interface LessonService {
    Response<?> createLesson(LessonDto.LessonCreteDto request);

    Response<?> getLesson(Long id);

    Response<?> getAllLesson(Pageable pageable);

    Response<?> updateLesson(LessonDto.LessonCreteDto request, Long id);

    Response<?> deleteLesson(Long id);

    Response<?> getLessonsByGroupId(Long groupId);

    Response<?> submit(AuthUser user, Long lessonId, AnswerDto answerDto);

    Response<?> getAllProgressByLessonIdAndUserId(Long lessonId, Long userId);

    Response<?> filter(Long groupId, String title, String description);
}
