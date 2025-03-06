package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
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
        return ResponseDTO.<List<Subject>>builder()
                .code(HttpStatus.OK.value())
                .message("Subject list successfully saved")
                .success(true)
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
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Subject successfully updated")
                .success(true)
                .build();
    }
}
