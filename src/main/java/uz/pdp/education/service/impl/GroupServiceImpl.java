package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.GroupDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.entity.SupportTeacher;
import uz.pdp.education.entity.Teacher;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.GroupMapper;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.repository.SupportTeacherRepository;
import uz.pdp.education.repository.TeacherRepository;
import uz.pdp.education.service.GroupService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {
    private final SupportTeacherRepository supportTeacherRepository;
    private final TeacherRepository teacherRepository;
    private final GroupsRepository groupsRepository;
    private final GroupMapper groupMapper;

    @Override
    public Response<?> createGroup(GroupDto groupDto, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + teacherId));
        Set<SupportTeacher> supportTeachers = new HashSet<>(supportTeacherRepository.findAll());
        Groups group = groupMapper.toEntity(groupDto);
        group.setTeacher(teacher);
        group.setSupports(supportTeachers);
        groupsRepository.save(group);
        log.info("Group successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Group successfully created")
                .success(true)
                .data(groupMapper.toDto(group))
                .build();
    }

    @Override
    public Response<?> getGroup(Long groupId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        log.info("Group successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Group successfully found")
                .success(true)
                .data(groupMapper.toDto(group))
                .build();
    }

    @Override
    public Response<?> getAllGroup() {
        List<Groups> groups = groupsRepository.findAll();
        if (!groups.isEmpty()) {
            log.info("Group successfully created");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .message("Group list successfully created")
                    .success(true)
                    .data(groupMapper.dtoList(groups))
                    .build();
        }
        log.error("Group list not found");
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Group list not found")
                .success(false)
                .build();
    }

    @Override
    public Response<?> updateGroup(GroupDto groupDto, Long groupId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        groupMapper.update(group, groupDto);
        groupsRepository.save(group);
        log.info("Group successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Group successfully updated")
                .success(true)
                .build();
    }
}
