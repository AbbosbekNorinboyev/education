package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.StudentCreateDTO;
import uz.pdp.education.entity.Student;
import uz.pdp.education.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/create")
    public ResponseDTO<Student> createStudent(@RequestBody StudentCreateDTO studentCreateDTO,
                                              @RequestParam Integer teacherId,
                                              @RequestParam Integer subjectId) {
        return studentService.createStudent(studentCreateDTO, teacherId, subjectId);
    }

    @GetMapping("/{studentId}")
    public ResponseDTO<Student> getStudent(@PathVariable Integer studentId) {
        return studentService.getStudent(studentId);
    }

    @GetMapping
    public ResponseDTO<List<Student>> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PutMapping("/update/{studentId}")
    public ResponseDTO<Void> updateStudent(@RequestBody StudentCreateDTO studentCreateDTO,
                                           @PathVariable Integer studentId) {
        return studentService.updateStudent(studentCreateDTO, studentId);
    }
}
