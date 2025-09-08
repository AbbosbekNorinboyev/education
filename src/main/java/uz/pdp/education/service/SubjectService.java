package uz.pdp.education.service;

import uz.pdp.education.dto.response.Response;
import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.entity.Subject;

import java.util.List;

public interface SubjectService {
    Response<Subject> createSubject(SubjectDto subjectDto, Integer teacherId);
    Response<Subject> getSubject(Integer subjectId);
    Response<List<Subject>> getAllSubject();
    Response<Void> updateSubject(SubjectDto subjectDto, Integer subjectId);
}
