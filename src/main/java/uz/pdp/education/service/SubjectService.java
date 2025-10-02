package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.dto.response.Response;

public interface SubjectService {
    Response<?> createSubject(SubjectDto subjectDto, Long teacherId);

    Response<?> getSubject(Long subjectId);

    Response<?> getAllSubject(Pageable pageable);

    Response<?> updateSubject(SubjectDto subjectDto);

    Response<?> addTeacherToSubject(Long teacherId, Long subjectId);
}
