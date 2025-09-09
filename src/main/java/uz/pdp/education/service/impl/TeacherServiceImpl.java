package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.TeacherDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.TeacherMapper;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.repository.StudentRepository;
import uz.pdp.education.repository.SubjectRepository;
import uz.pdp.education.repository.TeacherRepository;
import uz.pdp.education.service.TeacherService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupsRepository groupsRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public Response<?> createTeacher(TeacherDto teacherDto) {
        Teacher teacher = teacherMapper.toEntity(teacherDto);
        teacherRepository.save(teacher);
        log.info("Teacher successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Teacher successfully saved")
                .success(true)
                .data(teacherMapper.toDto(teacher))
                .build();
    }

    @Override
    public Response<?> getTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        log.info("Teacher successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Teacher successfully found")
                .success(true)
                .data(teacherMapper.toDto(teacher))
                .build();
    }

    @Override
    public Response<?> getAllTeacher() {
        List<Teacher> teachers = teacherRepository.findAll();
        if (!teachers.isEmpty()) {
            log.info("Teacher list successfully found");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Teacher list successfully found")
                    .success(true)
                    .data(teacherMapper.dtoList(teachers))
                    .build();
        }
        log.error("Teacher list not found");
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Teacher list not found")
                .success(false)
                .build();
    }

    @Override
    public Response<?> updateTeacher(TeacherDto teacherDto) {
        Teacher teacher = teacherRepository.findById(teacherDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherDto.getId()));
        teacherMapper.update(teacher, teacherDto);
        teacherRepository.save(teacher);
        log.info("Teacher successfully updated");
        return Response.builder()
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
    public Response<?> deleteTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        subjectRepository.deleteSubjectByTeacherId(Math.toIntExact(teacher.getId())); // subject ni ichida teacher ni id si bo'lgani uchun
        groupsRepository.deleteGroupByTeacherId(Math.toIntExact(teacher.getId())); // groups ni ichida teacher ni id si bo'lgani uchun
        studentRepository.deleteStudentByTeacherId(Math.toIntExact(teacher.getId())); // student ni ichida teacher ni id si bo'lgani uchun
        teacherRepository.delete(teacher);
        log.info("Teacher successfully deleted");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Teacher successfully deleted")
                .success(true)
                .build();
    }
}
