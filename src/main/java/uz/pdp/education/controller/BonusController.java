package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.BonusRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.enums.Months;
import uz.pdp.education.service.BonusService;

@RestController
@RequestMapping("/api/bonuses")
@RequiredArgsConstructor
public class BonusController {
    private final BonusService bonusService;

    @PostMapping("/create")
    public Response<?> create(@RequestBody BonusRequest request) {
        return bonusService.create(request);
    }

    @GetMapping("/get")
    public Response<?> get(@RequestParam Long id) {
        return bonusService.get(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return bonusService.getAll(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response<?> update(@RequestBody BonusRequest request,
                              @RequestParam Long id) {
        return bonusService.update(request, id);
    }

    @DeleteMapping("/delete")
    public Response<?> delete(@RequestParam Long id) {
        return bonusService.delete(id);
    }

    @GetMapping("/getBonusCountByUserId")
    public Response<?> getBonusCountByUserId(@RequestParam Long userId) {
        return bonusService.getBonusCountByUserId(userId);
    }

    @GetMapping("/getBonusCountByMonth")
    public Response<?> getBonusCountByMonth(@RequestParam Months month) {
        return bonusService.getBonusCountByMonth(month);
    }

    @GetMapping("/getBonusStatisticsMonth")
    public Response<?> getBonusStatisticsMonth() {
        return bonusService.getBonusStatisticsMonth();
    }
}
