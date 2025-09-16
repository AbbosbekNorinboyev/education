package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.SubjectMapper;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.repository.SubjectRepository;
import uz.pdp.education.service.SubjectService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final AuthUserRepository authUserRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public Response<?> createSubject(SubjectDto subjectDto, Long teacherId) {
        Subject subject = subjectMapper.toEntity(subjectDto);
        subjectRepository.save(subject);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Subject successfully saved")
                .success(true)
                .data(subjectMapper.toDto(subject))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Subject successfully found")
                .success(true)
                .data(subjectMapper.toDto(subject))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        if (!subjects.isEmpty()) {
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Subject list successfully found")
                    .success(true)
                    .data(subjectMapper.dtoList(subjects))
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .message("Subject list not found")
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> updateSubject(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(subjectDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectDto.getId()));
        subjectMapper.update(subject, subjectDto);
        subjectRepository.save(subject);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Subject successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> addTeacherToSubject(Long teacherId, Long subjectId) {
        AuthUser teacher = authUserRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        if (subject.getTeachers() != null && !subject.getTeachers().isEmpty()) {
            subject.getTeachers().add(teacher);
        } else {
            subject.setTeachers(new HashSet<>(Set.of(teacher)));
        }
        subjectRepository.save(subject);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Add teacher to subject")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
