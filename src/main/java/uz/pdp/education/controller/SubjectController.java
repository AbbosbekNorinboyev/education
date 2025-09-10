package uz.pdp.education.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/create")
    public Response<?> createSubject(@Valid @RequestBody SubjectDto subjectDto,
                                     @RequestParam Long teacherId) {
        return subjectService.createSubject(subjectDto, teacherId);
    }

    @GetMapping("/get")
    public Response<?> getSubject(@RequestParam Long subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @GetMapping("/getAll")
    public Response<?> getAllSubject() {
        return subjectService.getAllSubject();
    }

    @PutMapping("/update")
    public Response<?> updateSubject(@RequestBody SubjectDto subjectDto) {
        return subjectService.updateSubject(subjectDto);
    }

    @PostMapping("/addTeacherToSubject")
    public Response<?> addTeacherToSubject(@RequestParam Long teacherId,
                                           @RequestParam Long subjectId) {
        return subjectService.addTeacherToSubject(teacherId, subjectId);
    }
}
