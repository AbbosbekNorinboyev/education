package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.Ids;
import uz.pdp.education.dto.UserDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public Response<?> get(Long id) {
        return null;
    }

    @Override
    public Response<?> getAll(Pageable pageable, String role, String query) {
        return null;
    }

    @Override
    public Response<?> update(Long id, UserDto dto) {
        return null;
    }

    @Override
    public Response<?> me(AuthUser user) {
        return null;
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
