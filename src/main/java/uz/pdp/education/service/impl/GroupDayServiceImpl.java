package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.request.GroupDayRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.GroupDay;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.GroupDayMapper;
import uz.pdp.education.repository.GroupDayRepository;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.service.GroupDayService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class GroupDayServiceImpl implements GroupDayService {
    private final GroupDayMapper groupDayMapper;
    private final GroupDayRepository groupDayRepository;
    private final GroupsRepository groupsRepository;

    @Override
    public Response<?> createGroupDay(GroupDayRequest request) {
        Groups group = groupsRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + request.getGroupId()));
        GroupDay groupDay = groupDayMapper.toEntity(request);
        groupDay.setGroup(group);
        groupDayRepository.save(groupDay);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("GroupDay successfully created")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getGroupDay(Long id) {
        GroupDay groupDay = groupDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupDay not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("GroupDay successfully found")
                .data(groupDayMapper.toResponse(groupDay))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllGroupDay(Pageable pageable) {
        List<GroupDay> groupDays = groupDayRepository.findAll(pageable).getContent();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("GroupDay successfully found")
                .data(groupDayMapper.responseList(groupDays))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> updateGroupDay(GroupDayRequest request, Long id) {
        GroupDay groupDay = groupDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupDay not found: " + id));
        groupDayMapper.update(groupDay, request);
        groupDayRepository.save(groupDay);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("GroupDay successfully updated")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> deleteGroupDay(Long id) {
        GroupDay groupDay = groupDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroupDay not found: " + id));
        groupDayRepository.delete(groupDay);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("GroupDay successfully deleted")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
