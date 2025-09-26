package uz.pdp.education.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.education.enums.Months;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountResponse {
    private Long id;
    private Long groupId;
    private Long studentId;
    private Double amount;
    private Months months;
    private String reason;
}