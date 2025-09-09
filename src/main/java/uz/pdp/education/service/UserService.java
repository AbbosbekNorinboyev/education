package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uz.pdp.education.dto.Ids;
import uz.pdp.education.dto.UserDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;

import java.time.LocalDate;
import java.util.List;

@Component
public interface UserService {

    Response<?> get(Long id);

    Response<?> getAll(Pageable pageable, String role, String query);

    Response<?> update(Long id, UserDto dto);

    Response<?> me(AuthUser user);

    Response<?> addStudentToGroup(List<Ids> dto, Long groupId);

    Response<?> getStudentByDateAndGroup(LocalDate localDate, Long groupId);

    Response<?> roleStatistics();

    Response<?> getAllByGroupId(Long groupId);

    Response<?> search(String fullName, String phoneNumber, String username);
}