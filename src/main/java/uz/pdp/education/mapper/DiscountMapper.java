package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.DiscountRequest;
import uz.pdp.education.dto.response.DiscountResponse;
import uz.pdp.education.entity.Discount;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiscountMapper {
    public Discount toEntity(DiscountRequest request) {
        return Discount.builder()
                .amount(request.getAmount())
                .months(request.getMonths())
                .reason(request.getReason())
                .build();
    }

    public DiscountResponse toResponse(Discount entity) {
        return DiscountResponse.builder()
                .id(entity.getId())
                .groupId(entity.getGroup() != null ? entity.getGroup().getId() : null)
                .studentId(entity.getStudent() != null ? entity.getStudent().getId() : null)
                .amount(entity.getAmount())
                .months(entity.getMonths())
                .reason(entity.getReason())
                .build();
    }

    public List<DiscountResponse> responseList(List<Discount> discounts) {
        if (discounts != null && !discounts.isEmpty()) {
            return discounts.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Discount discount, DiscountRequest dto) {
        if (dto == null) {
            return;
        }
        if (dto.getAmount() != null) {
            discount.setAmount(dto.getAmount());
        }
        if (dto.getMonths() != null) {
            discount.setMonths(dto.getMonths());
        }
        if (dto.getReason() != null) {
            discount.setReason(dto.getReason());
        }
    }
}
