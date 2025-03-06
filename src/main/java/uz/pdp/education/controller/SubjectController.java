package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.SubjectCreateDTO;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping("/create")
    public ResponseDTO<Subject> createSubject(@RequestBody SubjectCreateDTO subjectCreateDTO,
                                              @RequestParam Integer teacherId) {
        return subjectService.createSubject(subjectCreateDTO, teacherId);
    }

    @GetMapping("/{subjectId}")
    public ResponseDTO<Subject> getSubject(@PathVariable Integer subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @GetMapping
    public ResponseDTO<List<Subject>> getAllSubject() {
        return subjectService.getAllSubject();
    }

    @PutMapping("/update/{subjectId}")
    public ResponseDTO<Void> updateSubject(@RequestBody SubjectCreateDTO subjectCreateDTO,
                                           @PathVariable Integer subjectId) {
        return subjectService.updateSubject(subjectCreateDTO, subjectId);
    }
}
