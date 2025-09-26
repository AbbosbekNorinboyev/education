package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.DiscountRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.DiscountService;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping("/create")
    public Response<?> addDiscountToStudent(@RequestBody DiscountRequest request) {
        return discountService.addDiscountToStudent(request);
    }

    @GetMapping("/get")
    public Response<?> get(@RequestParam Long id) {
        return discountService.get(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return discountService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("/getDiscountCountForGroup")
    public Response<?> getDiscountCountForGroup(@RequestParam Long groupId,
                                                @RequestParam String months) {
        return discountService.getDiscountCountForGroup(groupId, months);
    }

    @GetMapping("/getDiscountCountByStudentId")
    public Response<?> getDiscountCountByStudentId(@RequestParam Long studentId) {
        return discountService.getDiscountCountByStudentId(studentId);
    }

    @GetMapping("/getDiscountStatisticsMonth")
    public Response<?> getDiscountStatisticsMonth() {
        return discountService.getDiscountStatisticsMonth();
    }
}
