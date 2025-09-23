package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.BonusRequest;
import uz.pdp.education.dto.response.BonusResponse;
import uz.pdp.education.entity.Bonus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BonusMapper {
    public Bonus toEntity(BonusRequest request) {
        return Bonus.builder()
                .balance(request.getBalance())
                .months(request.getMonths())
                .build();
    }

    public BonusResponse toResponse(Bonus entity) {
        return BonusResponse.builder()
                .id(entity.getId())
                .authUserId(entity.getAuthUser() != null ? entity.getAuthUser().getId() : null)
                .balance(entity.getBalance())
                .months(entity.getMonths())
                .build();
    }

    public List<BonusResponse> responseList(List<Bonus> bonuses) {
        if (bonuses != null && !bonuses.isEmpty()) {
            return bonuses.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Bonus entity, BonusRequest request) {
        if (request == null) {
            return;
        }
        if (request.getBalance() != null) {
            entity.setBalance(request.getBalance());
        }
        if (request.getMonths() != null) {
            entity.setMonths(request.getMonths());
        }
    }

    public BonusResponse.BonusMonth toBonusMonthDto(Object[] row) {
        Double balance = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
        String months = row[0] != null ? (String) row[0] : null;
        return BonusResponse.BonusMonth.builder()
                .month(months)
                .balance(balance)
                .build();
    }

    public List<BonusResponse.BonusMonth> toBonusMonthDtoList(List<Object[]> rows) {
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }
        return rows.stream()
                .map(this::toBonusMonthDto)
                .toList();
    }
}
