package uz.pdp.education.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.TeacherDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.TeacherService;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/create")
    public Response<?> createTeacher(@RequestBody @Valid TeacherDto teacherDto) {
        return teacherService.createTeacher(teacherDto);
    }

    @GetMapping("/get")
    public Response<?> getTeacher(@RequestParam Integer teacherId) {
        return teacherService.getTeacher(teacherId);
    }

    @GetMapping("/getAll")
    public Response<?> getAllTeacher() {
        return teacherService.getAllTeacher();
    }

    @PutMapping("/update")
    public Response<?> updateTeacher(@RequestBody TeacherDto teacherDto) {
        return teacherService.updateTeacher(teacherDto);
    }

    @DeleteMapping("/delete")
    public Response<?> deleteTeacher(@RequestParam Integer teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }
}
