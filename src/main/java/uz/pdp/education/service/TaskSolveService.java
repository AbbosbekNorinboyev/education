package uz.pdp.education.service;

import uz.pdp.education.dto.TaskSolveDto;
import uz.pdp.education.dto.response.Response;

public interface TaskSolveService {
    Response<?> create(TaskSolveDto.CreateTaskSolve dto);

    Response<?> submit(TaskSolveDto.SubmitTaskSolve dto);

    Response<?> getAllByLessonId(Long lessonId);

    Response<?> filter(Long studentId, Long teacherId, Long lessonId, Long groupId);

    Response<?> resultsFilter(Long studentId, Long teacherId, Long lessonId, Long groupId);
}