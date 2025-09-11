package uz.pdp.education.service;

import uz.pdp.education.dto.request.GroupDayRequest;
import uz.pdp.education.dto.response.Response;

public interface GroupDayService {
    Response<?> createGroupDay(GroupDayRequest request);

    Response<?> getGroupDay(Long id);

    Response<?> getAllGroupDay();

    Response<?> updateGroupDay(GroupDayRequest request, Long id);

    Response<?> deleteGroupDay(Long id);
}
