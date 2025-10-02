package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.AboutUsRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.AboutUsService;

@RestController
@RequestMapping("/api/aboutUses")
@RequiredArgsConstructor
public class AboutUsController {
    private final AboutUsService aboutUsService;

    @PostMapping("/create")
    public Response<?> createUs(@RequestBody AboutUsRequest request) {
        return aboutUsService.createUs(request);
    }

    @GetMapping("/get")
    public Response<?> getUs(@RequestParam Long id) {
        return aboutUsService.getUs(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAllUs(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return aboutUsService.getAllUs(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response<?> updateUs(@RequestBody AboutUsRequest request,
                                @RequestParam Long id) {
        return aboutUsService.updateUs(request, id);
    }

    @DeleteMapping("/delete")
    public Response<?> deleteUs(@RequestParam Long id) {
        return aboutUsService.deleteUs(id);
    }
}
