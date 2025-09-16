package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.request.StudentAttendanceRequest;
import uz.pdp.education.dto.request.TeacherAttendanceRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.entity.TeacherAttendance;
import uz.pdp.education.enums.Role;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.TeacherAttendanceMapper;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.repository.TeacherAttendanceRepository;
import uz.pdp.education.service.TeacherAttendanceService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TeacherAttendanceServiceImpl implements TeacherAttendanceService {
    private final GroupsRepository groupsRepository;
    private final AuthUserRepository authUserRepository;
    private final TeacherAttendanceRepository teacherAttendanceRepository;
    private final TeacherAttendanceMapper teacherAttendanceMapper;

    @Override
    public Response<?> create(TeacherAttendanceRequest request, Long groupId, Long teacherId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        AuthUser teacher = authUserRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        TeacherAttendance teacherAttendance = teacherAttendanceMapper.toEntity(request);
        teacherAttendance.setGroup(group);
        if (teacher.getRole() != Role.TEACHER) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .message("ROLE mos kelmadi")
                    .success(false)
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        teacherAttendance.setTeacher(teacher);
        teacherAttendanceRepository.save(teacherAttendance);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("TeacherAttendance successfully saved")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> get(Long id) {
        TeacherAttendance teacherAttendance = teacherAttendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeacherAttendance not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("TeacherAttendance successfully found")
                .success(true)
                .data(teacherAttendanceMapper.toResponse(teacherAttendance))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll() {
        List<TeacherAttendance> teacherAttendances = teacherAttendanceRepository.findAll();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("TeacherAttendance list successfully found")
                .success(true)
                .data(teacherAttendanceMapper.dtoList(teacherAttendances))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> update(TeacherAttendanceRequest request, Long id) {
        TeacherAttendance teacherAttendance = teacherAttendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeacherAttendance not found: " + id));
        teacherAttendanceMapper.updateTeacherAttendance(teacherAttendance, request);
        teacherAttendanceRepository.save(teacherAttendance);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("TeacherAttendance successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
