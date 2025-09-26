package uz.pdp.education.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.GroupRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.GroupService;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    public Response<?> createGroup(@Valid @RequestBody GroupRequest groupRequest) {
        return groupService.createGroup(groupRequest);
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
    public Response<?> updateGroup(@RequestBody GroupRequest groupRequest,
                                   @RequestParam Long groupId) {
        return groupService.updateGroup(groupRequest, groupId);
    }

    @GetMapping("/getGroupTeacherId")
    public Response<?> getGroupTeacherId(@RequestParam Long teacherId) {
        return groupService.getGroupsTeacherId(teacherId);
    }

    @GetMapping("/getGroupSubjectId")
    public Response<?> getGroupSubjectId(@RequestParam Long subjectId) {
        return groupService.getGroupsSubjectId(subjectId);
    }

    @GetMapping("/getGroupsBySupportTeacherId")
    public Response<?> getGroupsBySupportTeacherId(@RequestParam Long supportTeacherId) {
        return groupService.getGroupsBySupportTeacherId(supportTeacherId);
    }

    @GetMapping("/filter")
    public Response<?> filter(@RequestParam(required = false) Long groupId,
                              @RequestParam(required = false) Long subjectId,
                              @RequestParam(required = false) Long teacherId) {
        return groupService.filter(groupId, subjectId, teacherId);
    }
}
