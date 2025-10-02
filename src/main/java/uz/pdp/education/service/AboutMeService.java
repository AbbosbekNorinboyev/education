package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.request.AboutMeRequest;
import uz.pdp.education.dto.response.Response;

public interface AboutMeService {
    Response<?> createMe(AboutMeRequest request);

    Response<?> getMe(Long id);

    Response<?> getAllMe(Pageable pageable);

    Response<?> updateMe(AboutMeRequest request, Long id);

    Response<?> deleteMe(Long id);
}
