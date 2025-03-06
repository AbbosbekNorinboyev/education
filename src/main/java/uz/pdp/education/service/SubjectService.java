package uz.pdp.education.service;

import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.SubjectCreateDTO;
import uz.pdp.education.entity.Subject;

import java.util.List;

public interface SubjectService {
    ResponseDTO<Subject> createSubject(SubjectCreateDTO subjectCreateDTO, Integer teacherId);
    ResponseDTO<Subject> getSubject(Integer subjectId);
    ResponseDTO<List<Subject>> getAllSubject();
    ResponseDTO<Void> updateSubject(SubjectCreateDTO subjectCreateDTO, Integer subjectId);
}
