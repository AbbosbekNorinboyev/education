package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.LessonRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.LessonService;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/create")
    public Response<?> createLesson(@RequestBody LessonRequest request) {
        return lessonService.createLesson(request);
    }

    @GetMapping("/get")
    public Response<?> getLesson(@RequestParam Long id) {
        return lessonService.getLesson(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAllLesson() {
        return lessonService.getAllLesson();
    }

    @PutMapping("/update")
    public Response<?> updateLesson(@RequestBody LessonRequest request,
                                    @RequestParam Long id) {
        return lessonService.updateLesson(request, id);
    }

    @DeleteMapping("/delete")
    public Response<?> deleteLesson(@RequestParam Long id) {
        return lessonService.deleteLesson(id);
    }

    @GetMapping("/getLessonsByGroupId")
    public Response<?> getLessonsByGroupId(@RequestParam Long groupId) {
        return lessonService.getLessonsByGroupId(groupId);
    }

    @GetMapping("/filter")
    public Response<?> filter(@RequestParam(required = false) Long groupId,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) String description) {
        return lessonService.filter(groupId, title, description);
    }
}
