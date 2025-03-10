package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.GroupCreateDTO;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.repository.TeacherRepository;
import uz.pdp.education.service.GroupService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final TeacherRepository teacherRepository;
    private final GroupsRepository groupsRepository;

    @Override
    public ResponseDTO<Groups> createGroup(GroupCreateDTO groupCreateDTO, Integer teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        Groups group = Groups.builder()
                .name(groupCreateDTO.getName())
                .teacher(teacher)
                .createdAt(groupCreateDTO.getCreatedAt())
                .build();
        groupsRepository.save(group);
        log.info("Group successfully created");
        return ResponseDTO.<Groups>builder()
                .code(HttpStatus.OK.value())
                .message("Group successfully created")
                .success(true)
                .data(group)
                .build();
    }

    @Override
    public ResponseDTO<Groups> getGroup(Integer groupId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        log.info("Group successfully found");
        return ResponseDTO.<Groups>builder()
                .code(HttpStatus.OK.value())
                .message("Group successfully found")
                .success(true)
                .data(group)
                .build();
    }

    @Override
    public ResponseDTO<List<Groups>> getAllGroup() {
        List<Groups> groups = groupsRepository.findAll();
        if (!groups.isEmpty()) {
            log.info("Group successfully created");
            return ResponseDTO.<List<Groups>>builder()
                    .code(HttpStatus.OK.value())
                    .message("Group list successfully created")
                    .success(true)
                    .data(groups)
                    .build();
        }
        log.error("Group list not found");
        return ResponseDTO.<List<Groups>>builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Group list not found")
                .success(false)
                .data(groups)
                .build();
    }

    @Override
    public ResponseDTO<Void> updateGroup(GroupCreateDTO groupCreateDTO, Integer groupId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        group.setName(groupCreateDTO.getName());
        group.setUpdatedAt(groupCreateDTO.getUpdatedAt());
        groupsRepository.save(group);
        log.info("Group successfully updated");
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Group successfully updated")
                .success(true)
                .build();
    }
}
