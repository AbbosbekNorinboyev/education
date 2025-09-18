package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.request.BudgetRequest;
import uz.pdp.education.dto.response.Response;

public interface BudgetService {
    Response<?> create(BudgetRequest request);

    Response<?> get(Long id);

    Response<?> getAll(Pageable pageable);

    Response<?> update(BudgetRequest request, Long id);

    Response<?> delete(Long id);

    Response<?> getTotalAmount();
}
