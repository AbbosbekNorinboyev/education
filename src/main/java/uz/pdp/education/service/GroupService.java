package uz.pdp.education.service;

import uz.pdp.education.dto.GroupCreateDTO;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.entity.Groups;

import java.util.List;

public interface GroupService {
    ResponseDTO<Groups> createGroup(GroupCreateDTO groupCreateDTO, Integer teacherId);
    ResponseDTO<Groups> getGroup(Integer groupId);
    ResponseDTO<List<Groups>> getAllGroup();
    ResponseDTO<Void> updateGroup(GroupCreateDTO groupCreateDTO, Integer groupId);
}
