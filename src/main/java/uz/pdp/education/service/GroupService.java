package uz.pdp.education.service;

import uz.pdp.education.dto.GroupDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Groups;

import java.util.List;

public interface GroupService {
    Response<Groups> createGroup(GroupDto groupDto, Integer teacherId);
    Response<Groups> getGroup(Integer groupId);
    Response<List<Groups>> getAllGroup();
    Response<Void> updateGroup(GroupDto groupDto, Integer groupId);
}
