package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.ErrorDTO;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.TeacherCreateDTO;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.repository.StudentRepository;
import uz.pdp.education.repository.SubjectRepository;
import uz.pdp.education.repository.TeacherRepository;
import uz.pdp.education.service.TeacherService;
import uz.pdp.education.validation.TeacherValidation;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupsRepository groupsRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherValidation teacherValidation;

    @Override
    public ResponseDTO<Teacher> createTeacher(TeacherCreateDTO teacherCreateDTO) {
        List<ErrorDTO> errors = teacherValidation.validate(teacherCreateDTO);
        if (!errors.isEmpty()) {
            return ResponseDTO.<Teacher>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Teacher validation error")
                    .success(false)
                    .errors(errors)
                    .build();
        }
        Teacher teacher = Teacher.builder()
                .fullName(teacherCreateDTO.getFullName())
                .age(teacherCreateDTO.getAge())
                .createdAt(teacherCreateDTO.getCreatedAt())
                .build();
        teacherRepository.save(teacher);
        return ResponseDTO.<Teacher>builder()
                .code(HttpStatus.OK.value())
                .message("Teacher successfully saved")
                .success(true)
                .data(teacher)
                .build();
    }

    @Override
    public ResponseDTO<Teacher> getTeacher(Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        return ResponseDTO.<Teacher>builder()
                .code(HttpStatus.OK.value())
                .message("Teacher successfully found")
                .success(true)
                .data(teacher)
                .build();
    }

    @Override
    public ResponseDTO<List<Teacher>> getAllTeacher() {
        List<Teacher> teachers = teacherRepository.findAll();
        return ResponseDTO.<List<Teacher>>builder()
                .code(HttpStatus.OK.value())
                .message("Teacher list successfully found")
                .success(true)
                .data(teachers)
                .build();
    }

    @Override
    public ResponseDTO<Void> updateTeacher(TeacherCreateDTO teacherCreateDTO, Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        teacher.setAge(teacherCreateDTO.getAge());
        teacher.setFullName(teacherCreateDTO.getFullName());
        teacher.setUpdatedAt(teacherCreateDTO.getUpdatedAt());
        teacherRepository.save(teacher);
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Teacher successfully updated")
                .success(true)
                .build();

    }

    /**
     * teacherni id si orqali teacherni o'zini va unga bog'langan barcha tabledagi malumotlarni o'chirib yuborish
     *
     * @param teacherId
     * @return
     */
    @Override
    public ResponseDTO<Void> deleteTeacher(Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        subjectRepository.deleteSubjectByTeacherId(teacher.getId()); // subject ni ichida teacher ni id si bo'lgani uchun
        groupsRepository.deleteGroupByTeacherId(teacher.getId()); // groups ni ichida teacher ni id si bo'lgani uchun
        studentRepository.deleteStudentByTeacherId(teacher.getId()); // student ni ichida teacher ni id si bo'lgani uchun
        teacherRepository.delete(teacher);
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Teacher successfully deleted")
                .success(true)
                .build();
    }
}
