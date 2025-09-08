package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.dto.StudentDto;
import uz.pdp.education.entity.Student;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.exception.ResourceNotFoundException;
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

    @Override
    public Response<Student> createStudent(StudentDto studentDto,
                                           Integer teacherId,
                                           Integer subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        Student student = Student.builder()
                .age(studentDto.getAge())
                .fullName(studentDto.getFullName())
                .teacher(teacher)
                .subject(subject)
                .createdAt(studentDto.getCreatedAt())
                .build();
        studentRepository.save(student);
        log.info("Student successfully created");
        return Response.<Student>builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully saved")
                .success(true)
                .data(student)
                .build();
    }

    @Override
    public Response<Student> getStudent(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        log.info("Student successfully found");
        return Response.<Student>builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully found")
                .success(true)
                .data(student)
                .build();
    }

    @Override
    public Response<List<Student>> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        if (!students.isEmpty()) {
            log.info("Student list successfully found");
            return Response.<List<Student>>builder()
                    .code(HttpStatus.OK.value())
                    .message("Student list successfully found")
                    .success(true)
                    .data(students)
                    .build();
        }
        log.error("Student list not found");
        return Response.<List<Student>>builder()
                .code(HttpStatus.OK.value())
                .message("Student list successfully found")
                .success(false)
                .data(students)
                .build();
    }

    @Override
    public Response<Void> updateStudent(StudentDto studentDto, Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        student.setAge(studentDto.getAge());
        student.setFullName(studentDto.getFullName());
        student.setUpdatedAt(studentDto.getUpdatedAt());
        studentRepository.save(student);
        log.info("Student successfully updated");
        return Response.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully updated")
                .success(true)
                .build();
    }
}
