package uz.pdp.education.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
import java.util.ArrayList;
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
    private final EntityManager entityManager;

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
    public Response<?> getAll(Pageable pageable) {
        List<StudentAttendance> studentAttendances = studentAttendanceRepository.findAll(pageable).getContent();
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

    @Override
    public Response<?> filter(Long groupId, Long lessonId, Long studentId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentAttendance> criteriaQuery = criteriaBuilder.createQuery(StudentAttendance.class);
        Root<StudentAttendance> root = criteriaQuery.from(StudentAttendance.class);

        List<Predicate> predicates = new ArrayList<>();

        if (groupId != null) {
            predicates.add(criteriaBuilder.equal(root.get("group").get("id"), groupId));
        }
        if (lessonId != null) {
            predicates.add(criteriaBuilder.equal(root.get("lesson").get("id"), lessonId));
        }
        if (studentId != null) {
            predicates.add(criteriaBuilder.equal(root.get("student").get("id"), studentId));
        }

        criteriaQuery.select(root).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        List<StudentAttendance> studentAttendances = entityManager.createQuery(criteriaQuery).getResultList();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("StudentAttendance list successfully found filter by group and lesson and student")
                .success(true)
                .data(studentAttendanceMapper.dtoList(studentAttendances))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
