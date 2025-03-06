package uz.pdp.education.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.TeacherCreateDTO;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.service.impl.TeacherServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherServiceImpl teacherService;

    @PostMapping("/create")
    public ResponseDTO<Teacher> createTeacher(@RequestBody @Valid TeacherCreateDTO teacherCreateDTO) {
        return teacherService.createTeacher(teacherCreateDTO);
    }

    @GetMapping("/{teacherId}")
    public ResponseDTO<Teacher> getTeacher(@PathVariable Integer teacherId) {
        return teacherService.getTeacher(teacherId);
    }

    @GetMapping
    public ResponseDTO<List<Teacher>> getAllTeacher() {
        return teacherService.getAllTeacher();
    }

    @PutMapping("/update/{teacherId}")
    public ResponseDTO<Void> updateTeacher(@RequestBody TeacherCreateDTO teacherCreateDTO,
                                           @PathVariable Integer teacherId) {
        return teacherService.updateTeacher(teacherCreateDTO, teacherId);
    }

    @DeleteMapping("/delete/{teacherId}")
    public ResponseDTO<Void> deleteTeacher(@PathVariable Integer teacherId) {
        return teacherService.deleteTeacher(teacherId);
    }
}
