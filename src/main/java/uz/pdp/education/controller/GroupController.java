package uz.pdp.education.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.GroupDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.GroupService;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    public Response<?> createGroup(@Valid @RequestBody GroupDto groupDto,
                                   @RequestParam Long teacherId) {
        return groupService.createGroup(groupDto, teacherId);
    }

    @GetMapping("/get")
    public Response<?> getGroup(@RequestParam Long groupId) {
        return groupService.getGroup(groupId);
    }

    @GetMapping("/getAll")
    public Response<?> getAllGroup() {
        return groupService.getAllGroup();
    }

    @PutMapping("/update")
    public Response<?> updateGroup(@RequestBody GroupDto groupDto,
                                   @RequestParam Long groupId) {
        return groupService.updateGroup(groupDto, groupId);
    }

    @GetMapping("/getGroupTeacherId")
    public Response<?> getGroupTeacherId(@RequestParam Long teacherId) {
        return groupService.getGroupTeacherId(teacherId);
    }
}
