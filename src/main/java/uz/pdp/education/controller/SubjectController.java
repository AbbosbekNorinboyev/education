package uz.pdp.education.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/create")
    public Response<Subject> createSubject(@Valid @RequestBody SubjectDto subjectDto,
                                           @RequestParam Integer teacherId) {
        return subjectService.createSubject(subjectDto, teacherId);
    }

    @GetMapping("/get")
    public Response<Subject> getSubject(@RequestParam Integer subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @GetMapping("/getAll")
    public Response<List<Subject>> getAllSubject() {
        return subjectService.getAllSubject();
    }

    @PutMapping("/update")
    public Response<Void> updateSubject(@RequestBody SubjectDto subjectDto,
                                        @RequestParam Integer subjectId) {
        return subjectService.updateSubject(subjectDto, subjectId);
    }
}
