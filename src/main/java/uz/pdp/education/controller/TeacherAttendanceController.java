package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.TeacherAttendanceRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.TeacherAttendanceService;

@RestController
@RequestMapping("/api/teacherAttendances")
@RequiredArgsConstructor
public class TeacherAttendanceController {
    private final TeacherAttendanceService teacherAttendanceService;

    @PostMapping("/create")
    public Response<?> create(@RequestBody TeacherAttendanceRequest request,
                              @RequestParam Long groupId,
                              @RequestParam Long teacherId) {
        return teacherAttendanceService.create(request, groupId, teacherId);
    }

    @GetMapping("/get")
    public Response<?> get(@RequestParam Long id) {
        return teacherAttendanceService.get(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAll() {
        return teacherAttendanceService.getAll();
    }

    @PutMapping("/update")
    public Response<?> update(@RequestBody TeacherAttendanceRequest request,
                              @RequestParam Long id) {
        return teacherAttendanceService.update(request, id);
    }

    @GetMapping("/filter")
    public Response<?> filter(@RequestParam(required = false) Long teacherId,
                              @RequestParam(required = false) Long groupId) {
        return teacherAttendanceService.filter(teacherId, groupId);
    }
}
