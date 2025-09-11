package uz.pdp.education.service.impl;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.ShortDto;
import uz.pdp.education.dto.UserDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.enums.Role;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.UserMapper;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.service.UserService;
import uz.pdp.education.specification.AuthUserSpecification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final GroupsRepository groupsRepository;
    private final AuthUserRepository authUserRepository;
    private final UserMapper userMapper;

    @Override
    public Response<?> get(Long id) {
        AuthUser authUser = authUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + id));
        log.info("AuthUser successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully found")
                .success(true)
                .data(userMapper.toDto(authUser))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        Page<UserDto> users = authUserRepository.findAll(pageable)
                .map(userMapper::toDto);
        log.info("AuthUser list successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser list successfully found")
                .data(users)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> update(UserDto dto) {
        AuthUser authUser = authUserRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + dto.getId()));
        userMapper.update(authUser, dto);
        authUserRepository.save(authUser);
        log.info("AuthUser successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully updated")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> me(AuthUser user) {
        if (user == null) {
            return Response.builder()
                    .message("USER IS NULL")
                    .build();
        }
        log.info("AuthUser successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully found")
                .data(userMapper.toDto(user))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> addStudentToGroup(List<Long> ids, Long groupId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        for (Long id : ids) {
            AuthUser authUser = authUserRepository.findById(id).orElse(null);
            if (authUser != null) {
                authUser.setRole(Role.STUDENT);
                if (group.getStudents() != null && !group.getStudents().isEmpty()) {
                    group.getStudents().add(authUser);
                } else {
                    group.setStudents(new HashSet<>(Set.of(authUser)));
                }
            }
        }
        groupsRepository.save(group);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Add students to group")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getStudentByDateAndGroup(LocalDate localDate, Long groupId) {
        return null;
    }

    @Override
    public Response<?> roleStatistics() {
        List<Tuple> roleStatistics = authUserRepository.roleStatistics();
        List<ShortDto> shortDtoList = new ArrayList<>();
        for (Tuple roleStatistic : roleStatistics) {
            shortDtoList.add(
                    new ShortDto(
                            String.valueOf(roleStatistic.get(1, Role.class)),
                            roleStatistic.get(0, Long.class)
                    )
            );
        }
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Role statistics")
                .data(shortDtoList)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllByGroupId(Long groupId) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        List<AuthUser> authUsers = authUserRepository.findAllByGroupId(groupId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully found by group id")
                .data(userMapper.dtoList(authUsers))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> search(String fullName, String phoneNumber, String username) {
        Specification<AuthUser> specification = Specification.where(null);
        if (fullName != null && !fullName.isEmpty()) {
            specification = specification.and(AuthUserSpecification.hasFullName(fullName));
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            specification = specification.and(AuthUserSpecification.hasFullName(phoneNumber));
        }
        if (username != null && !username.isEmpty()) {
            specification = specification.and(AuthUserSpecification.hasFullName(username));
        }
        List<AuthUser> authUsers = authUserRepository.findAll(specification);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("AuthUser successfully found")
                .data(userMapper.dtoList(authUsers))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
