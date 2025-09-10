package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.UserDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.service.UserService;
import uz.pdp.education.utils.validator.CurrentUser;

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
}
