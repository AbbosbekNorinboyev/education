package uz.pdp.education.service;

import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.dto.response.Response;

public interface SubjectService {
    Response<?> createSubject(SubjectDto subjectDto, Long teacherId);

    Response<?> getSubject(Long subjectId);

    Response<?> getAllSubject();

    Response<?> updateSubject(SubjectDto subjectDto);
}
