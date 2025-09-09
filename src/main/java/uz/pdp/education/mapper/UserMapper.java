package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.UserDto;
import uz.pdp.education.entity.AuthUser;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public AuthUser toEntity(UserDto dto) {
        return AuthUser.builder()
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .balance(dto.getBalance())
                .role(dto.getRole())
                .build();
    }

    public UserDto toDto(AuthUser entity) {
        return UserDto.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .phoneNumber(entity.getPhoneNumber())
                .password(entity.getPassword())
                .username(entity.getUsername())
                .balance(entity.getBalance())
                .role(entity.getRole())
                .build();
    }

    public List<UserDto> dtoList(List<AuthUser> users) {
        if (users != null && !users.isEmpty()) {
            return users.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(AuthUser entity, UserDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getFullName() != null && !dto.getFullName().trim().isEmpty()) {
            entity.setFullName(dto.getFullName());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().trim().isEmpty()) {
            entity.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            entity.setPassword(dto.getPassword());
        }
        if (dto.getUsername() != null && !dto.getUsername().trim().isEmpty()) {
            entity.setUsername(dto.getUsername());
        }
        if (dto.getBalance() != null) {
            entity.setBalance(dto.getBalance());
        }
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
    }
}
