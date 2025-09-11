package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.UserDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.service.UserService;
import uz.pdp.education.utils.validator.CurrentUser;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public Response<?> get(@RequestParam Long id) {
        return userService.get(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return userService.getAll(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response<?> update(@RequestBody UserDto dto) {
        return userService.update(dto);
    }

    @GetMapping("/me")
    public Response<?> me(@CurrentUser AuthUser authUser) {
        return userService.me(authUser);
    }

    @PostMapping("/addStudentToGroup")
    public Response<?> addStudentToGroup(@RequestParam List<Long> ids,
                                         @RequestParam Long groupId) {
        return userService.addStudentToGroup(ids, groupId);
    }

    @GetMapping("/getStudentByDateAndGroup")
    public Response<?> getStudentByDateAndGroup(LocalDate localDate, Long groupId) {
        return null;
    }

    @GetMapping("/roleStatistics")
    public Response<?> roleStatistics() {
        return userService.roleStatistics();
    }

    @GetMapping("/getAllByGroupId")
    public Response<?> getAllByGroupId(@RequestParam Long groupId) {
        return userService.getAllByGroupId(groupId);
    }

    @GetMapping("/search")
    public Response<?> search(@RequestParam(required = false) String fullName,
                              @RequestParam(required = false) String phoneNumber,
                              @RequestParam(required = false) String username) {
        return userService.search(fullName, phoneNumber, username);
    }
}
