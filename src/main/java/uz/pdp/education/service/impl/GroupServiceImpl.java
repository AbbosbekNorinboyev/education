package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.request.GroupRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.entity.Subject;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.GroupMapper;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.repository.SubjectRepository;
import uz.pdp.education.service.GroupService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {
    private final SubjectRepository subjectRepository;
    private final AuthUserRepository authUserRepository;
    private final GroupsRepository groupsRepository;
    private final GroupMapper groupMapper;

    @Override
    public Response<?> createGroup(GroupRequest groupRequest) {
        AuthUser teacher = authUserRepository.findById(groupRequest.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + groupRequest.getTeacherId()));
        Subject subject = subjectRepository.findById(groupRequest.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + groupRequest.getSubjectId()));
        Groups group = groupMapper.toEntity(groupRequest);
        group.setTeacher(teacher);
        group.setSubject(subject);
        groupsRepository.save(group);
        log.info("Group successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Group successfully created")
                .success(true)
                .data(groupMapper.toResponse(group))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getGroup(Long groupId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        log.info("Group successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Group successfully found")
                .success(true)
                .data(groupMapper.toResponse(group))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllGroup() {
        List<Groups> groups = groupsRepository.findAll();
        if (!groups.isEmpty()) {
            log.info("Group list successfully found");
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Group list successfully found")
                    .success(true)
                    .data(groupMapper.dtoList(groups))
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        log.error("Group list not found");
        return Response.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND)
                .message("Group list not found")
                .success(false)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> updateGroup(GroupRequest groupRequest, Long groupId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        groupMapper.update(group, groupRequest);
        groupsRepository.save(group);
        log.info("Group successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Group successfully updated")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getGroupTeacherId(Long teacherId) {
        List<Groups> groups = groupsRepository.findAllByTeacherId(teacherId);
        log.info("Group list successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Group list successfully found by teacher id")
                .success(true)
                .data(groupMapper.dtoList(groups))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
