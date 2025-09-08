package uz.pdp.education.service;

import uz.pdp.education.dto.response.Response;
import uz.pdp.education.dto.StudentDto;
import uz.pdp.education.entity.Student;

import java.util.List;

public interface StudentService {
    Response<Student> createStudent(StudentDto studentDto, Integer teacherId, Integer subjectId);
    Response<Student> getStudent(Integer studentId);
    Response<List<Student>> getAllStudent();
    Response<Void> updateStudent(StudentDto studentDto, Integer studentId);
}
