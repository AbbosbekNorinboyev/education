package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.TeacherAttendanceRequest;
import uz.pdp.education.dto.response.TeacherAttendanceResponse;
import uz.pdp.education.entity.TeacherAttendance;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeacherAttendanceMapper {
    public TeacherAttendance toEntity(TeacherAttendanceRequest request) {
        return TeacherAttendance.builder()
                .active(request.getActive())
                .build();
    }

    public TeacherAttendanceResponse toResponse(TeacherAttendance entity) {
        return TeacherAttendanceResponse.builder()
                .id(entity.getId())
                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
                .groupId(entity.getGroup() != null ? entity.getGroup().getId() : null)
                .active(entity.getActive())
                .build();
    }

    public List<TeacherAttendanceResponse> dtoList(List<TeacherAttendance> studentAttendances) {
        if (studentAttendances != null && !studentAttendances.isEmpty()) {
            return studentAttendances.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void updateTeacherAttendance(TeacherAttendance entity, TeacherAttendanceRequest request) {
        if (request == null) {
            return;
        }
        if (request.getActive() != null) {
            entity.setActive(request.getActive());
        }
    }
}
