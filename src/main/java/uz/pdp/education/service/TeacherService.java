package uz.pdp.education.service;

import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.dto.TeacherCreateDTO;
import uz.pdp.education.entity.Teacher;

import java.util.List;

public interface TeacherService {
    ResponseDTO<Teacher> createTeacher(TeacherCreateDTO teacherCreateDTO);

    ResponseDTO<Teacher> getTeacher(Integer teacherId);

    ResponseDTO<List<Teacher>> getAllTeacher();

    ResponseDTO<Void> updateTeacher(TeacherCreateDTO teacherCreateDTO, Integer teacherId);

    ResponseDTO<Void> deleteTeacher(Integer teacherId);
}
