package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.SubjectMapper;
import uz.pdp.education.repository.SubjectRepository;
import uz.pdp.education.repository.TeacherRepository;
import uz.pdp.education.service.SubjectService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public Response<?> createSubject(SubjectDto subjectDto, Long teacherId) {
        Set<Teacher> teachers = new HashSet<>(teacherRepository.findAll());
        Subject subject = subjectMapper.toEntity(subjectDto);
        subject.setTeachers(teachers);
        subjectRepository.save(subject);
        log.info("Subject successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Subject successfully saved")
                .success(true)
                .data(subjectMapper.toDto(subject))
                .build();
    }

    @Override
    public Response<?> getSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        log.info("Subject successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Subject successfully found")
                .success(true)
                .data(subjectMapper.toDto(subject))
                .build();
    }

    @Override
    public Response<?> getAllSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        if (!subjects.isEmpty()) {
            log.info("Subject list successfully found");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Subject list successfully found")
                    .success(true)
                    .data(subjectMapper.dtoList(subjects))
                    .build();
        }
        log.error("Subject list not found");
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Subject list not found")
                .success(false)
                .build();
    }

    @Override
    public Response<?> updateSubject(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findById(subjectDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectDto.getId()));
        subjectMapper.update(subject, subjectDto);
        subjectRepository.save(subject);
        log.info("Subject successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Subject successfully updated")
                .success(true)
                .build();
    }
}
