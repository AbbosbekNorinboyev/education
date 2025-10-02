package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.request.BonusRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.Bonus;
import uz.pdp.education.enums.BudgetType;
import uz.pdp.education.enums.Months;
import uz.pdp.education.enums.Role;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.BonusMapper;
import uz.pdp.education.mapper.BudgetMapper;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.repository.BonusRepository;
import uz.pdp.education.repository.BudgetRepository;
import uz.pdp.education.service.BonusService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService {
    private final BonusMapper bonusMapper;
    private final AuthUserRepository authUserRepository;
    private final BonusRepository bonusRepository;
    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;

    @Override
    public Response<?> create(BonusRequest request) {
        AuthUser authUser = authUserRepository.findById(request.getAuthUserId())
                .orElseThrow(() -> new ResourceNotFoundException("AuthUser not found: " + request.getAuthUserId()));
        Bonus bonus = bonusMapper.toEntity(request);
        if (authUser.getRole() != Role.STUDENT) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .message("ROLE mos kelmadi")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        bonus.setAuthUser(authUser);
        budgetMapper.addBudget("BONUS OLDI", BudgetType.OUTCOME, request.getBalance(), budgetRepository);
        bonusRepository.save(bonus);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Bonus successfully saved")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> get(Long id) {
        Bonus bonus = bonusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bonus not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Bonus successfully found")
                .data(bonusMapper.toResponse(bonus))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        List<Bonus> bonuses = bonusRepository.findAll(pageable).getContent();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Bonus list successfully found")
                .data(bonusMapper.responseList(bonuses))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> update(BonusRequest request, Long id) {
        Bonus bonus = bonusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bonus not found: " + id));
        bonusMapper.update(bonus, request);
        bonusRepository.save(bonus);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Bonus successfully updated")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> delete(Long id) {
        Bonus bonus = bonusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bonus not found: " + id));
        bonusRepository.delete(bonus);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Bonus successfully deleted")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getBonusCountByUserId(Long userId) {
        List<Bonus> bonuses = bonusRepository.findAllByAuthUserId(userId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Bonus successfully found by user id")
                .data(bonusMapper.responseList(bonuses))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getBonusCountByMonth(Months month) {
        List<Bonus> bonuses = bonusRepository.findAllByMonths(month.name());
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Bonus successfully found by month")
                .data(bonusMapper.responseList(bonuses))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getBonusStatisticsMonth() {
        List<Object[]> allByMonthsNative = bonusRepository.findAllByMonthsNative();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Bonus successfully found")
                .data(bonusMapper.toBonusMonthDtoList(allByMonthsNative))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
