package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.ErrorDTO;
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
import uz.pdp.education.validation.StudentValidation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final StudentValidation studentValidation;

    @Override
    public ResponseDTO<Student> createStudent(StudentCreateDTO studentCreateDTO,
                                              Integer teacherId,
                                              Integer subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        List<ErrorDTO> errors = studentValidation.validate(studentCreateDTO);
        if (!errors.isEmpty()){
            return ResponseDTO.<Student>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Student validation error")
                    .success(false)
                    .errors(errors)
                    .build();
        }
        Student student = Student.builder()
                .age(studentCreateDTO.getAge())
                .fullName(studentCreateDTO.getFullName())
                .teacher(teacher)
                .subject(subject)
                .createdAt(studentCreateDTO.getCreatedAt())
                .build();
        studentRepository.save(student);
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
        return ResponseDTO.<List<Student>>builder()
                .code(HttpStatus.OK.value())
                .message("Student list successfully found")
                .success(true)
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
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Student successfully updated")
                .success(true)
                .build();
    }
}
