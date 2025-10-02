package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.request.LessonRequest;
import uz.pdp.education.dto.response.Response;

public interface LessonService {
    Response<?> createLesson(LessonRequest request);

    Response<?> getLesson(Long id);

    Response<?> getAllLesson(Pageable pageable);

    Response<?> updateLesson(LessonRequest request, Long id);

    Response<?> deleteLesson(Long id);

    Response<?> getLessonsByGroupId(Long groupId);

    Response<?> filter(Long groupId, String title, String description);
}
