package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.RoomRequest;
import uz.pdp.education.dto.response.RoomResponse;
import uz.pdp.education.entity.Room;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomMapper {
    public Room toEntity(RoomRequest request) {
        return Room.builder()
                .name(request.getName())
                .number(request.getNumber())
                .description(request.getDescription())
                .capacity(request.getCapacity())
                .build();
    }

    public RoomResponse toResponse(Room entity) {
        return RoomResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .number(entity.getNumber())
                .description(entity.getDescription())
                .capacity(entity.getCapacity())
                .build();
    }

    public List<RoomResponse> responseList(List<Room> rooms) {
        if (rooms != null && !rooms.isEmpty()) {
            return rooms.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Room entity, RoomRequest request) {
        if (request == null) {
            return;
        }
        if (request.getNumber() != null) {
            entity.setNumber(request.getNumber());
        }
        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            entity.setDescription(request.getDescription());
        }
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            entity.setName(request.getName());
        }
        if (request.getCapacity() != null) {
            entity.setCapacity(request.getCapacity());
        }
    }
}
