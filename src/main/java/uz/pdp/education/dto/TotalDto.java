package uz.pdp.education.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalDto {
    private Double totalIncome;
    private Double totalOutcome;
    private Double totalAmount;
}