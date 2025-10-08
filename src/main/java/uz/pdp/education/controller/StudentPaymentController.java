package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.StudentPaymentDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.StudentPaymentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student-payment")
public class StudentPaymentController {
    private final StudentPaymentService studentPaymentService;

    @PostMapping("/create")
    public Response<?> create(@RequestBody StudentPaymentDto dto) {
        return studentPaymentService.create(dto);
    }

    @GetMapping
    public Response<?> getAll(@RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        return studentPaymentService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("/studentId")
    public Response<?> getByStudentId(@RequestParam Long studentId,
                                      @RequestParam(value = "month", required = false) String month) {
        return studentPaymentService.getByStudentId(studentId, month);
    }

    @DeleteMapping("/id")
    public Response<?> delete(@RequestParam Long id) {
        return studentPaymentService.delete(id);
    }

    @GetMapping("/groupId")
    public Response<?> getByGroupId(@RequestParam Long groupId) {
        return studentPaymentService.getByGroupId(groupId);
    }

    @GetMapping("/getStudentPaymentStatisticsMonth")
    public Response<?> getStudentPaymentStatisticsMonth() {
        return studentPaymentService.getStudentPaymentStatisticsMonth();
    }
}
