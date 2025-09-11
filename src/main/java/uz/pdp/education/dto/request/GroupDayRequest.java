package uz.pdp.education.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.education.enums.Weeks;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDayRequest {
    private Weeks weeks;
    private String groupTime;
    private Long groupId;
}
