package uz.pdp.education.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.StudentDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Student;
import uz.pdp.education.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/create")
    public Response<Student> createStudent(@Valid @RequestBody StudentDto studentDto,
                                           @RequestParam Integer teacherId,
                                           @RequestParam Integer subjectId) {
        return studentService.createStudent(studentDto, teacherId, subjectId);
    }

    @GetMapping("/get")
    public Response<Student> getStudent(@RequestParam Integer studentId) {
        return studentService.getStudent(studentId);
    }

    @GetMapping("/getAll")
    public Response<List<Student>> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PutMapping("/update")
    public Response<Void> updateStudent(@RequestBody StudentDto studentDto,
                                        @RequestParam Integer studentId) {
        return studentService.updateStudent(studentDto, studentId);
    }
}
