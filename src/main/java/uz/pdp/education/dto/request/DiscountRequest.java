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
public class DiscountRequest {
    private Long groupId;
    private Long studentId;
    private Double amount;
    private Months months;
    private String reason;
}