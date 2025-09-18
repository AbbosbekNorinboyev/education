package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.BudgetRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.BudgetService;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping("/create")
    public Response<?> create(@RequestBody BudgetRequest request) {
        return budgetService.create(request);
    }

    @GetMapping("/get")
    public Response<?> get(@RequestParam Long id) {
        return budgetService.get(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return budgetService.getAll(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response<?> update(@RequestBody BudgetRequest request,
                              @RequestParam Long id) {
        return budgetService.update(request, id);
    }

    @DeleteMapping("/delete")
    public Response<?> delete(@RequestParam Long id) {
        return budgetService.delete(id);
    }

    @GetMapping("/getTotalAmount")
    public Response<?> getTotalAmount() {
        return budgetService.getTotalAmount();
    }
}
