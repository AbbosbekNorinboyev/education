package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.Ids;
import uz.pdp.education.dto.UserDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.enums.Role;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.UserMapper;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
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
    public Response<?> addStudentToGroup(List<Ids> dto, Long groupId) {
        return null;
    }

    @Override
    public Response<?> getStudentByDateAndGroup(LocalDate localDate, Long groupId) {
        return null;
    }

    @Override
    public Response<?> roleStatistics() {
        return null;
    }

    @Override
    public Response<?> getAllByGroupId(Long groupId) {
        return null;
    }

    @Override
    public Response<?> search(String fullName, String phoneNumber, String username) {
        return null;
    }
}
