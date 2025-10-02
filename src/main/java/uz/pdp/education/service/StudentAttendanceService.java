package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.request.StudentAttendanceRequest;
import uz.pdp.education.dto.response.Response;

public interface StudentAttendanceService {
    Response<?> create(StudentAttendanceRequest request, Long groupId, Long studentId, Long lessonId);

    Response<?> get(Long id);

    Response<?> getAll(Pageable pageable);

    Response<?> update(StudentAttendanceRequest request, Long id);

    Response<?> filter(Long groupId, Long lessonId, Long studentId);
}
