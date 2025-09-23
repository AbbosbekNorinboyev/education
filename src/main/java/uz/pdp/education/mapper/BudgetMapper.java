package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.BudgetRequest;
import uz.pdp.education.dto.response.BudgetResponse;
import uz.pdp.education.entity.Budget;
import uz.pdp.education.enums.BudgetType;
import uz.pdp.education.repository.BudgetRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class BudgetMapper {
    public Budget toEntity(BudgetRequest request) {
        return Budget.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .build();
    }

    public BudgetResponse toResponse(Budget entity) {
        return BudgetResponse.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .budgetType(entity.getBudgetType())
                .build();
    }

    public List<BudgetResponse> responseList(List<Budget> budgets) {
        if (budgets != null && !budgets.isEmpty()) {
            return budgets.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Budget entity, BudgetRequest request) {
        if (request == null) {
            return;
        }
        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            entity.setDescription(request.getDescription());
        }
        if (request.getAmount() != null) {
            entity.setAmount(request.getAmount());
        }
    }

    public void addBudget(String description, BudgetType budgetType,
                          Double balance, BudgetRepository budgetRepository) {
        Budget budget = new Budget();
        budget.setBudgetType(budgetType);
        budget.setAmount(balance);
        budget.setDescription(description);
        budgetRepository.save(budget);
    }
}
