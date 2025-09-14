package uz.pdp.education.service;

import uz.pdp.education.dto.request.LessonRequest;
import uz.pdp.education.dto.response.Response;

public interface LessonService {
    Response<?> createLesson(LessonRequest request);

    Response<?> getLesson(Long id);

    Response<?> getAllLesson();

    Response<?> updateLesson(LessonRequest request, Long id);

    Response<?> deleteLesson(Long id);

    Response<?> getLessonsByGroupId(Long groupId);
}
