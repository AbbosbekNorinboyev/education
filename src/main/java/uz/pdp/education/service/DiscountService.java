package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.request.DiscountRequest;
import uz.pdp.education.dto.response.Response;

public interface DiscountService {
    Response<?> addDiscountToStudent(DiscountRequest request);

    Response<?> get(Long id);

    Response<?> getAll(Pageable pageable);

    Response<?> getDiscountCountForGroup(Long groupId, String months);

    Response<?> getDiscountCountByStudentId(Long studentId);

    Response<?> getDiscountStatisticsMonth();
}
