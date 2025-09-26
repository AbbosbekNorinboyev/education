package uz.pdp.education.service;

import uz.pdp.education.dto.request.GroupRequest;
import uz.pdp.education.dto.response.Response;

public interface GroupService {
    Response<?> createGroup(GroupRequest groupRequest);

    Response<?> getGroup(Long groupId);

    Response<?> getAllGroup();

    Response<?> updateGroup(GroupRequest groupRequest, Long groupId);

    Response<?> getGroupsTeacherId(Long teacherId);

    Response<?> getGroupsSubjectId(Long subjectId);

    Response<?> getGroupsBySupportTeacherId(Long supportTeacherId);

    Response<?> filter(Long groupId, Long subjectId, Long teacherId);
}
