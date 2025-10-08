package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.TaskSolveDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.TaskSolveService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task-solve")
public class TaskSolveController {
    private final TaskSolveService taskSolveService;

    @PostMapping(value = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> create(@ModelAttribute TaskSolveDto.CreateTaskSolve dto) {
        return taskSolveService.create(dto);
    }

    @PostMapping("/submit")
    public Response<?> submit(@RequestBody TaskSolveDto.SubmitTaskSolve dto) {
        return taskSolveService.submit(dto);
    }

    @GetMapping("/getAllByLessonId")
    public Response<?> getAllByLessonId(@RequestParam("lessonId") Long lessonId) {
        return taskSolveService.getAllByLessonId(lessonId);
    }

    @GetMapping("/results")
    public Response<?> filter(@RequestParam(value = "studentId", required = false) Long studentId,
                              @RequestParam(value = "teacherId", required = false) Long teacherId,
                              @RequestParam(value = "lessonId", required = false) Long lessonId,
                              @RequestParam(value = "groupId", required = false) Long groupId) {
        return taskSolveService.filter(studentId, teacherId, lessonId, groupId);
    }

    @GetMapping("/resultsFilter")
    public Response<?> resultsFilter(@RequestParam(value = "studentId", required = false) Long studentId,
                                     @RequestParam(value = "teacherId", required = false) Long teacherId,
                                     @RequestParam(value = "lessonId", required = false) Long lessonId,
                                     @RequestParam(value = "groupId", required = false) Long groupId) {
        return taskSolveService.resultsFilter(studentId, teacherId, lessonId, groupId);
    }
}