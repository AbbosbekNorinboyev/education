package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.GroupCreateDTO;
import uz.pdp.education.dto.ResponseDTO;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.service.impl.GroupServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupServiceImpl groupService;

    @PostMapping("/create")
    public ResponseDTO<Groups> createGroup(@RequestBody GroupCreateDTO groupCreateDTO,
                                           @RequestParam Integer teacherId) {
        return groupService.createGroup(groupCreateDTO, teacherId);
    }

    @GetMapping("/{groupId}")
    public ResponseDTO<Groups> getGroup(@PathVariable Integer groupId) {
        return groupService.getGroup(groupId);
    }

    @GetMapping
    public ResponseDTO<List<Groups>> getAllGroup() {
        return groupService.getAllGroup();
    }

    @PutMapping("/update/{groupId}")
    public ResponseDTO<Void> updateGroup(@RequestBody GroupCreateDTO groupCreateDTO,
                                         @PathVariable Integer groupId) {
        return groupService.updateGroup(groupCreateDTO, groupId);
    }
}
