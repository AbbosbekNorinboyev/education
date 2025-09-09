package uz.pdp.education.service;

import uz.pdp.education.dto.TeacherDto;
import uz.pdp.education.dto.response.Response;

public interface TeacherService {
    Response<?> createTeacher(TeacherDto teacherDto);

    Response<?> getTeacher(Long teacherId);

    Response<?> getAllTeacher();

    Response<?> updateTeacher(TeacherDto teacherDto);

    Response<?> deleteTeacher(Long teacherId);
}
