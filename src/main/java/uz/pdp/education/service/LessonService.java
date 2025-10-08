package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.AnswerDto;
import uz.pdp.education.dto.request.LessonRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;

public interface LessonService {
    Response<?> createLesson(LessonRequest request);

    Response<?> getLesson(Long id);

    Response<?> getAllLesson(Pageable pageable);

    Response<?> updateLesson(LessonRequest request, Long id);

    Response<?> deleteLesson(Long id);

    Response<?> getLessonsByGroupId(Long groupId);

    Response<?> submit(AuthUser user, Long lessonId, AnswerDto answerDto);

    Response<?> getAllProgressByLessonIdAndUserId(Long lessonId, Long userId);

    Response<?> filter(Long groupId, String title, String description);
}
