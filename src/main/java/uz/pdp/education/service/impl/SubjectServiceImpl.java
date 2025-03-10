package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.SubjectCreateDTO;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.repository.SubjectRepository;
import uz.pdp.education.repository.TeacherRepository;
import uz.pdp.education.service.SubjectService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public ResponseDTO<Subject> createSubject(SubjectCreateDTO subjectCreateDTO, Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        Subject subject = Subject.builder()
                .name(subjectCreateDTO.getName())
                .teacher(teacher)
                .createdAt(subjectCreateDTO.getCreatedAt())
                .build();
        subjectRepository.save(subject);
        log.info("Subject successfully created");
        return ResponseDTO.<Subject>builder()
                .code(HttpStatus.OK.value())
                .message("Subject successfully saved")
                .success(true)
                .data(subject)
                .build();
    }

    @Override
    public ResponseDTO<Subject> getSubject(Integer subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        log.info("Subject successfully found");
        return ResponseDTO.<Subject>builder()
                .code(HttpStatus.OK.value())
                .message("Subject successfully found")
                .success(true)
                .data(subject)
                .build();
    }

    @Override
    public ResponseDTO<List<Subject>> getAllSubject() {
        List<Subject> subjects = subjectRepository.findAll();
        if (!subjects.isEmpty()) {
            log.info("Subject list successfully found");
            return ResponseDTO.<List<Subject>>builder()
                    .code(HttpStatus.OK.value())
                    .message("Subject list successfully found")
                    .success(true)
                    .data(subjects)
                    .build();
        }
        log.error("Subject list not found");
        return ResponseDTO.<List<Subject>>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Subject list not found")
                .success(false)
                .data(subjects)
                .build();
    }

    @Override
    public ResponseDTO<Void> updateSubject(SubjectCreateDTO subjectCreateDTO, Integer subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));
        subject.setName(subjectCreateDTO.getName());
        subject.setUpdatedAt(subjectCreateDTO.getUpdatedAt());
        subjectRepository.save(subject);
        log.info("Subject successfully updated");
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Subject successfully updated")
                .success(true)
                .build();
    }
}
