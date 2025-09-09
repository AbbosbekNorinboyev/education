package uz.pdp.education.service;

import uz.pdp.education.dto.StudentDto;
import uz.pdp.education.dto.response.Response;

public interface StudentService {
    Response<?> createStudent(StudentDto studentDto, Long teacherId, Long subjectId);

    Response<?> getStudent(Long studentId);

    Response<?> getAllStudent();

    Response<?> updateStudent(StudentDto studentDto);
}
