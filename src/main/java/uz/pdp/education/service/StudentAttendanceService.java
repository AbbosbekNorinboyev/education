package uz.pdp.education.service;

import uz.pdp.education.dto.request.StudentAttendanceRequest;
import uz.pdp.education.dto.response.Response;

public interface StudentAttendanceService {
    Response<?> create(StudentAttendanceRequest request, Long groupId, Long studentId, Long lessonId);

    Response<?> get(Long id);

    Response<?> getAll();

    Response<?> update(StudentAttendanceRequest request, Long id);
}
