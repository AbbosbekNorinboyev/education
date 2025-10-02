package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.request.TeacherAttendanceRequest;
import uz.pdp.education.dto.response.Response;

public interface TeacherAttendanceService {
    Response<?> create(TeacherAttendanceRequest request, Long groupId, Long teacherId);

    Response<?> get(Long id);

    Response<?> getAll(Pageable pageable);

    Response<?> update(TeacherAttendanceRequest request, Long id);

    Response<?> filter(Long teacherId, Long groupId);
}
