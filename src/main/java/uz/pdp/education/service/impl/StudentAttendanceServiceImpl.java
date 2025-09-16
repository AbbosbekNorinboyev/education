package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.request.StudentAttendanceRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.entity.Lesson;
import uz.pdp.education.entity.StudentAttendance;
import uz.pdp.education.enums.Role;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.StudentAttendanceMapper;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.repository.LessonRepository;
import uz.pdp.education.repository.StudentAttendanceRepository;
import uz.pdp.education.service.StudentAttendanceService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class StudentAttendanceServiceImpl implements StudentAttendanceService {
    private final StudentAttendanceRepository studentAttendanceRepository;
    private final StudentAttendanceMapper studentAttendanceMapper;
    private final GroupsRepository groupsRepository;
    private final AuthUserRepository authUserRepository;
    private final LessonRepository lessonRepository;

    @Override
    public Response<?> create(StudentAttendanceRequest request, Long groupId, Long studentId, Long lessonId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        AuthUser student = authUserRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        StudentAttendance studentAttendance = studentAttendanceMapper.toEntity(request);
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson not found: " + lessonId));
        if (student.getRole() != Role.STUDENT) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .message("ROLE mos kelmadi")
                    .success(false)
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        studentAttendance.setStudent(student);
        studentAttendance.setGroup(group);
        studentAttendance.setLesson(lesson);
        studentAttendanceRepository.save(studentAttendance);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("StudentAttendance successfully saved")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> get(Long id) {
        StudentAttendance studentAttendance = studentAttendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StudentAttendance not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("StudentAttendance successfully found")
                .success(true)
                .data(studentAttendanceMapper.toResponse(studentAttendance))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll() {
        List<StudentAttendance> studentAttendances = studentAttendanceRepository.findAll();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("StudentAttendance list successfully found")
                .success(true)
                .data(studentAttendanceMapper.dtoList(studentAttendances))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> update(StudentAttendanceRequest request, Long id) {
        StudentAttendance studentAttendance = studentAttendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StudentAttendance not found: " + id));
        studentAttendanceMapper.update(studentAttendance, request);
        studentAttendanceRepository.save(studentAttendance);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("StudentAttendance successfully updated")
                .success(true)
                .data(studentAttendanceMapper.toResponse(studentAttendance))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
