package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.StudentAttendanceRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.StudentAttendanceService;

@RestController
@RequestMapping("/api/studentAttendances")
@RequiredArgsConstructor
public class StudentAttendanceController {
    private final StudentAttendanceService studentAttendanceService;

    @PostMapping("/create")
    public Response<?> create(@RequestBody StudentAttendanceRequest request,
                              @RequestParam Long groupId,
                              @RequestParam Long studentId,
                              @RequestParam Long lessonId) {
        return studentAttendanceService.create(request, groupId, studentId, lessonId);
    }

    @GetMapping("/get")
    public Response<?> get(@RequestParam Long id) {
        return studentAttendanceService.get(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return studentAttendanceService.getAll(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response<?> update(@RequestBody StudentAttendanceRequest request,
                              @RequestParam Long id) {
        return studentAttendanceService.update(request, id);
    }

    @GetMapping("/filter")
    public Response<?> filter(@RequestParam(required = false) Long groupId,
                              @RequestParam(required = false) Long lessonId,
                              @RequestParam(required = false) Long studentId) {
        return studentAttendanceService.filter(groupId, lessonId, studentId);
    }
}
