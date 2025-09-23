package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.request.BonusRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.enums.Months;

public interface BonusService {
    Response<?> create(BonusRequest request);

    Response<?> get(Long id);

    Response<?> getAll(Pageable pageable);

    Response<?> update(BonusRequest request, Long id);

    Response<?> delete(Long id);

    Response<?> getBonusCountByUserId(Long userId);

    Response<?> getBonusCountByMonth(Months month);

    Response<?> getBonusStatisticsMonth();
}
