package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.StudentAttendanceRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.StudentAttendanceService;

@RestController
@RequestMapping("/api/studentAttendance")
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
    public Response<?> getAll() {
        return studentAttendanceService.getAll();
    }

    @PutMapping("/update")
    public Response<?> update(@RequestBody StudentAttendanceRequest request,
                              @RequestParam Long id) {
        return studentAttendanceService.update(request, id);
    }
}
