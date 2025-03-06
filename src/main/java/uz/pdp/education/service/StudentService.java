package uz.pdp.education.service;

import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.StudentCreateDTO;
import uz.pdp.education.entity.Student;

import java.util.List;

public interface StudentService {
    ResponseDTO<Student> createStudent(StudentCreateDTO studentCreateDTO, Integer teacherId, Integer subjectId);
    ResponseDTO<Student> getStudent(Integer studentId);
    ResponseDTO<List<Student>> getAllStudent();
    ResponseDTO<Void> updateStudent(StudentCreateDTO studentCreateDTO, Integer studentId);
}
