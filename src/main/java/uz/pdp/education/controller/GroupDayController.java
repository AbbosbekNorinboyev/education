package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.GroupDayRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.GroupDayService;

@RestController
@RequestMapping("/api/groupDays")
@RequiredArgsConstructor
public class GroupDayController {
    private final GroupDayService groupDayService;

    @PostMapping("/create")
    public Response<?> createGroupDay(@RequestBody GroupDayRequest request) {
        return groupDayService.createGroupDay(request);
    }

    @GetMapping("/get")
    public Response<?> getGroupDay(@RequestParam Long id) {
        return groupDayService.getGroupDay(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAllGroupDay() {
        return groupDayService.getAllGroupDay();
    }

    @PutMapping("/update")
    public Response<?> updateGroupDay(@RequestBody GroupDayRequest request,
                                      @RequestParam Long id) {
        return groupDayService.updateGroupDay(request, id);
    }

    @DeleteMapping("/delete")
    public Response<?> deleteGroupDay(@RequestParam Long id) {
        return groupDayService.deleteGroupDay(id);
    }
}
