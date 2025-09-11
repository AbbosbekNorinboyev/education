package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
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
    public Response<?> getAllMe() {
        return aboutMeService.getAllMe();
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
