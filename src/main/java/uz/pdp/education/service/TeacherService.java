package uz.pdp.education.service;

import uz.pdp.education.dto.response.Response;
import uz.pdp.education.dto.TeacherDto;
import uz.pdp.education.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Response<Teacher> createTeacher(TeacherDto teacherDto);

    Response<Teacher> getTeacher(Integer teacherId);

    Response<List<Teacher>> getAllTeacher();

    Response<Void> updateTeacher(TeacherDto teacherDto, Integer teacherId);

    Response<Void> deleteTeacher(Integer teacherId);
}
