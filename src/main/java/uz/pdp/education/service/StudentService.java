package uz.pdp.education.service;

import uz.pdp.education.dto.StudentDto;
import uz.pdp.education.dto.response.Response;

public interface StudentService {
    Response<?> createStudent(StudentDto studentDto, Integer teacherId, Integer subjectId);

    Response<?> getStudent(Integer studentId);

    Response<?> getAllStudent();

    Response<?> updateStudent(StudentDto studentDto);
}
