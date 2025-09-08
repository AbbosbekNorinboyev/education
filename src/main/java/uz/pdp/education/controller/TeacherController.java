package uz.pdp.education.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.TeacherDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.service.impl.TeacherServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherServiceImpl teacherService;

    @PostMapping("/create")
    public Response<Teacher> createTeacher(@RequestBody @Valid TeacherDto teacherDto) {
        return teacherService.createTeacher(teacherDto);
    }

    @GetMapping("/get")
    public Response<Teacher> getTeacher(@RequestParam Integer teacherId) {
        return teacherService.getTeacher(teacherId);
    }

    @GetMapping("/getAll")
    public Response<List<Teacher>> getAllTeacher() {
        return teacherService.getAllTeacher();
    }

    @PutMapping("/update")
    public Response<Void> updateTeacher(@RequestBody TeacherDto teacherDto,
                                        @RequestParam Integer teacherId) {
        return teacherService.updateTeacher(teacherDto, teacherId);
    }

    @DeleteMapping("/delete")
    public Response<Void> deleteTeacher(@RequestParam Integer teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }
}
