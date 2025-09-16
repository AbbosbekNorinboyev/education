package uz.pdp.education.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequest {
    private String name;
    private Integer number;
    private String description;
    private Integer capacity;
}