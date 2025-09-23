package uz.pdp.education.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.education.enums.Months;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BonusRequest {
    private Long authUserId;
    private Double balance;
    private Months months;
}