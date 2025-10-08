package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.StudentPaymentDto;
import uz.pdp.education.dto.response.Response;

public interface StudentPaymentService {
    Response<?> create(StudentPaymentDto dto);

    Response<?> getAll(Pageable pageable);

    Response<?> getByStudentId(Long studentId, String month);

    Response<?> delete(Long id);

    Response<?> getByGroupId(Long groupId);

    Response<?> getStudentPaymentStatisticsMonth();
}