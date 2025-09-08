package uz.pdp.education.service;

import uz.pdp.education.dto.GroupDto;
import uz.pdp.education.dto.response.Response;

public interface GroupService {
    Response<?> createGroup(GroupDto groupDto, Integer teacherId);

    Response<?> getGroup(Integer groupId);

    Response<?> getAllGroup();

    Response<?> updateGroup(GroupDto groupDto, Integer groupId);
}
