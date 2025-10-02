package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.AboutMeRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.AboutMeService;

@RestController
@RequestMapping("/api/aboutMes")
@RequiredArgsConstructor
public class AboutMeController {
    private final AboutMeService aboutMeService;

    @PostMapping("/create")
    public Response<?> createMe(@RequestBody AboutMeRequest request) {
        return aboutMeService.createMe(request);
    }

    @GetMapping("/get")
    public Response<?> getMe(@RequestParam Long id) {
        return aboutMeService.getMe(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAllMe(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return aboutMeService.getAllMe(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response<?> updateMe(@RequestBody AboutMeRequest request,
                                @RequestParam Long id) {
        return aboutMeService.updateMe(request, id);
    }

    @DeleteMapping("/delete")
    public Response<?> deleteMe(@RequestParam Long id) {
        return aboutMeService.deleteMe(id);
    }
}
