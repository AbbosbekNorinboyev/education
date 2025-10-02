package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.TotalDto;
import uz.pdp.education.dto.request.BudgetRequest;
import uz.pdp.education.dto.response.BudgetResponse;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.Budget;
import uz.pdp.education.enums.BudgetType;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.BudgetMapper;
import uz.pdp.education.repository.BudgetRepository;
import uz.pdp.education.service.BudgetService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;

    @Override
    public Response<?> create(BudgetRequest request) {
        Budget budget = budgetMapper.toEntity(request);
        budget.setBudgetType(BudgetType.INCOME);
        budgetRepository.save(budget);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Budget successfully saved")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> get(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Budget successfully found")
                .data(budgetMapper.toResponse(budget))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        List<Budget> budgets = budgetRepository.findAll(pageable).getContent();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Budget successfully found")
                .data(budgetMapper.responseList(budgets))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> update(BudgetRequest request, Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + id));
        budgetMapper.update(budget, request);
        budgetRepository.save(budget);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Budget successfully updated")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> delete(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found: " + id));
        budgetRepository.delete(budget);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Budget successfully deleted")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getTotalAmount() {
        Double income = budgetRepository.findByBudgetType(BudgetType.INCOME.name());
        Double outcome = budgetRepository.findByBudgetType(BudgetType.OUTCOME.name());
        Double totalAmount = (income != null ? income : 0) - (outcome != null ? outcome : 0);
        TotalDto totalDto = TotalDto.builder()
                .totalIncome(income)
                .totalOutcome(outcome)
                .totalAmount(totalAmount)
                .build();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Budget successfully calculate")
                .data(totalDto)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
