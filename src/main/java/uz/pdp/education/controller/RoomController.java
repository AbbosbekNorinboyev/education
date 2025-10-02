package uz.pdp.education.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import uz.pdp.education.dto.request.RoomRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    public Response<?> createRoom(@RequestBody RoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping("/get")
    public Response<?> getRoom(@RequestParam Long id) {
        return roomService.getRoom(id);
    }

    @GetMapping("/getAll")
    public Response<?> getAllRoom(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return roomService.getAllRoom(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response<?> updateRoom(@RequestBody RoomRequest request,
                                  @RequestParam Long id) {
        return roomService.updateRoom(request, id);
    }

    @DeleteMapping("/delete")
    public Response<?> deleteRoom(@RequestParam Long id) {
        return roomService.deleteRoom(id);
    }
}
