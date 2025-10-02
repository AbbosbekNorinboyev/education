package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.request.AboutUsRequest;
import uz.pdp.education.dto.response.Response;

public interface AboutUsService {
    Response<?> createUs(AboutUsRequest request);

    Response<?> getUs(Long id);

    Response<?> getAllUs(Pageable pageable);

    Response<?> updateUs(AboutUsRequest request, Long id);

    Response<?> deleteUs(Long id);
}
