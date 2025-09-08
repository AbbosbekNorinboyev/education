package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.StudentCreateDTO;
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
    public ResponseDTO<Student> createStudent(StudentCreateDTO studentCreateDTO,
                                              Integer teacherId,
                                              Integer subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        Student student = Student.builder()
                .age(studentCreateDTO.getAge())
                .fullName(studentCreateDTO.getFullName())
                .teacher(teacher)
                .subject(subject)
                .createdAt(studentCreateDTO.getCreatedAt())
                .build();
        studentRepository.save(student);
        log.info("Student successfully created");
        return ResponseDTO.<Student>builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully saved")
                .success(true)
                .data(student)
                .build();
    }

    @Override
    public ResponseDTO<Student> getStudent(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        log.info("Student successfully found");
        return ResponseDTO.<Student>builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully found")
                .success(true)
                .data(student)
                .build();
    }

    @Override
    public ResponseDTO<List<Student>> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        if (!students.isEmpty()) {
            log.info("Student list successfully found");
            return ResponseDTO.<List<Student>>builder()
                    .code(HttpStatus.OK.value())
                    .message("Student list successfully found")
                    .success(true)
                    .data(students)
                    .build();
        }
        log.error("Student list not found");
        return ResponseDTO.<List<Student>>builder()
                .code(HttpStatus.OK.value())
                .message("Student list successfully found")
                .success(false)
                .data(students)
                .build();
    }

    @Override
    public ResponseDTO<Void> updateStudent(StudentCreateDTO studentCreateDTO, Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        student.setAge(studentCreateDTO.getAge());
        student.setFullName(studentCreateDTO.getFullName());
        student.setUpdatedAt(studentCreateDTO.getUpdatedAt());
        studentRepository.save(student);
        log.info("Student successfully updated");
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully updated")
                .success(true)
                .build();
    }
}
