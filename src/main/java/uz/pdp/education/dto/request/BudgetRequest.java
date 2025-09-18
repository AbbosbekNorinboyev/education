package uz.pdp.education.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.education.enums.BudgetType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetRequest {
    private String description;
    private Double amount;
    private BudgetType budgetType;
}