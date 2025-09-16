package uz.pdp.education.service;

import uz.pdp.education.dto.request.RoomRequest;
import uz.pdp.education.dto.response.Response;

public interface RoomService {
    Response<?> createRoom(RoomRequest request);

    Response<?> getRoom(Long id);

    Response<?> getAllRoom();

    Response<?> updateRoom(RoomRequest request, Long id);

    Response<?> deleteRoom(Long id);
}
