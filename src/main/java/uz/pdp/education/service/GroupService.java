package uz.pdp.education.service;

import uz.pdp.education.dto.GroupDto;
import uz.pdp.education.dto.response.Response;

public interface GroupService {
    Response<?> createGroup(GroupDto groupDto, Long teacherId);

    Response<?> getGroup(Long groupId);

    Response<?> getAllGroup();

    Response<?> updateGroup(GroupDto groupDto, Long groupId);
}
