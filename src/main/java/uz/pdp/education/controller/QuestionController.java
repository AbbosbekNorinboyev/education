package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.QuestionDto;
import uz.pdp.education.dto.RequestTestDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/create")
    public Response<?> create(@RequestBody QuestionDto.QuestionCreateDto dto,
                              @RequestParam("lessonId") Long lessonId) {
        return questionService.create(lessonId, dto);
    }

    @GetMapping("/get")
    public Response<?> get(@RequestParam("id") Long id) {
        return questionService.get(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return questionService.getAll(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response<?> update(@RequestBody QuestionDto.QuestionCreateDto dto,
                              @RequestParam("id") Long id) {
        return questionService.update(dto, id);
    }

    @DeleteMapping("/delete")
    public Response<?> delete(@RequestParam("id") Long id) {
        return questionService.delete(id);
    }

    @GetMapping("/getByLessonId")
    public Response<?> getByLessonId(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                     @RequestParam("lessonId") Long lessonId) {
        return questionService.getByLessonId(PageRequest.of(page, size), lessonId);
    }

    @PostMapping("/test")
    public Response<?> resultTest(@RequestBody List<RequestTestDto> requestTestDto,
                                  @RequestParam(name = "lessonId") Long lessonId,
                                  @RequestParam("studentId") Long studentId) {
        return questionService.test(requestTestDto, studentId, lessonId);
    }

}
