package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.StudentAttendanceRequest;
import uz.pdp.education.dto.response.StudentAttendanceResponse;
import uz.pdp.education.entity.StudentAttendance;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentAttendanceMapper {
    public StudentAttendance toEntity(StudentAttendanceRequest request) {
        return StudentAttendance.builder()
                .active(request.getActive())
                .build();
    }

    public StudentAttendanceResponse toResponse(StudentAttendance entity) {
        return StudentAttendanceResponse.builder()
                .id(entity.getId())
                .groupId(entity.getGroup() != null ? entity.getGroup().getId() : null)
                .studentId(entity.getStudent() != null ? entity.getStudent().getId() : null)
                .lessonId(entity.getLesson() != null ? entity.getLesson().getId() : null)
                .active(entity.getActive())
                .build();
    }

    public List<StudentAttendanceResponse> dtoList(List<StudentAttendance> studentAttendances) {
        if (studentAttendances != null && !studentAttendances.isEmpty()) {
            return studentAttendances.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(StudentAttendance entity, StudentAttendanceRequest request) {
        if (request == null) {
            return;
        }
        if (request.getActive() != null) {
            entity.setActive(request.getActive());
        }
    }
}
