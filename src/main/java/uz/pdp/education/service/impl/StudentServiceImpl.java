package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.StudentDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Student;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.StudentMapper;
import uz.pdp.education.repository.StudentRepository;
import uz.pdp.education.repository.SubjectRepository;
import uz.pdp.education.repository.TeacherRepository;
import uz.pdp.education.service.StudentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public Response<?> createStudent(StudentDto studentDto,
                                     Integer teacherId,
                                     Integer subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        Student student = studentMapper.toEntity(studentDto);
        student.setTeacher(teacher);
        student.setSubject(subject);
        studentRepository.save(student);
        log.info("Student successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully saved")
                .success(true)
                .data(studentMapper.toDto(student))
                .build();
    }

    @Override
    public Response<?> getStudent(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        log.info("Student successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully found")
                .success(true)
                .data(studentMapper.toDto(student))
                .build();
    }

    @Override
    public Response<?> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        if (!students.isEmpty()) {
            log.info("Student list successfully found");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Student list successfully found")
                    .success(true)
                    .data(studentMapper.dtoList(students))
                    .build();
        }
        log.error("Student list not found");
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Student list successfully found")
                .success(false)
                .build();
    }

    @Override
    public Response<?> updateStudent(StudentDto studentDto) {
        Student student = studentRepository.findById(Math.toIntExact(studentDto.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentDto.getId()));
        studentMapper.update(student, studentDto);
        studentRepository.save(student);
        log.info("Student successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully updated")
                .success(true)
                .build();
    }
}
