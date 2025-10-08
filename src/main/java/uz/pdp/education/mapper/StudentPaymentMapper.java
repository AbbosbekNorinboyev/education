package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.StudentPaymentDto;
import uz.pdp.education.entity.StudentPayment;

import java.util.Collections;
import java.util.List;

@Component
public class StudentPaymentMapper {
    public StudentPayment toEntity(StudentPaymentDto dto) {
        return StudentPayment.builder()
                .amount(dto.getAmount())
                .months(dto.getMonths())
                .build();
    }

    public StudentPaymentDto toDto(StudentPayment entity) {
        return StudentPaymentDto.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .months(entity.getMonths())
                .year(entity.getYear())
                .groupId(entity.getGroup() != null ? entity.getGroup().getId() : null)
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public List<StudentPaymentDto> dtoList(List<StudentPayment> entities) {
        if (entities != null && !entities.isEmpty()) {
            return entities.stream().map(this::toDto).toList();
        }
        return null;
    }

    public void update(StudentPaymentDto dto, StudentPayment entity) {
        if (dto == null) {
            return;
        }
        if (dto.getMonths() != null) {
            entity.setMonths(dto.getMonths());
        }
        if (dto.getAmount() != null && dto.getAmount() >= 0) {
            entity.setAmount(dto.getAmount());
        }
    }

    public StudentPaymentDto.StudentPaymentMonth toStudentPaymentMonthDto(Object[] row) {
        Double amount = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
        String months = row[0] != null ? (String) row[0] : null;
        return StudentPaymentDto.StudentPaymentMonth.builder()
                .months(months)
                .amount(amount)
                .build();
    }

    public List<StudentPaymentDto.StudentPaymentMonth> dtoStudentPaymentMonthList(List<Object[]> rows) {
        if (rows == null || rows.isEmpty()) {
            return Collections.emptyList();
        }
        return rows.stream()
                .map(this::toStudentPaymentMonthDto)
                .toList();
    }
}
