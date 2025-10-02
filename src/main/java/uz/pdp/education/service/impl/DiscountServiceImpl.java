package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.DiscountMonthDto;
import uz.pdp.education.dto.request.DiscountRequest;
import uz.pdp.education.dto.response.DiscountResponse;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.Discount;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.enums.BudgetType;
import uz.pdp.education.enums.Role;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.BudgetMapper;
import uz.pdp.education.mapper.DiscountMapper;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.repository.BudgetRepository;
import uz.pdp.education.repository.DiscountRepository;
import uz.pdp.education.repository.GroupsRepository;
import uz.pdp.education.service.DiscountService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountMapper discountMapper;
    private final DiscountRepository discountRepository;
    private final GroupsRepository groupsRepository;
    private final AuthUserRepository authUserRepository;
    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;

    @Override
    public Response<?> addDiscountToStudent(DiscountRequest request) {
        Groups group = groupsRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + request.getGroupId()));
        AuthUser student = authUserRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + request.getStudentId()));
        Discount discount = discountMapper.toEntity(request);
        discount.setGroup(group);
        if (!student.getRole().equals(Role.STUDENT)) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Student ROLE mos kelmadi")
                    .success(false)
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        discount.setStudent(student);
        budgetMapper.addBudget("CHEGIRMA OLDI", BudgetType.OUTCOME, request.getAmount(), budgetRepository);
        discountRepository.save(discount);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Discount successfully created")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> get(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Discount successfully found")
                .success(true)
                .data(discountMapper.toResponse(discount))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        List<Discount> discounts = discountRepository.findAll(pageable).getContent();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Discount list successfully found")
                .success(true)
                .data(discountMapper.responseList(discounts))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getDiscountCountForGroup(Long groupId, String months) {
        Groups group = groupsRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found: " + groupId));
        List<Discount> discounts = discountRepository.findAllByGroupIdAndMonths(group.getId(), months);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Discount list successfully found for group")
                .success(true)
                .data(discountMapper.responseList(discounts))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getDiscountCountByStudentId(Long studentId) {
        AuthUser student = authUserRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));
        if (!student.getRole().equals(Role.STUDENT)) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Student ROLE mos kelmadi")
                    .success(false)
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        List<Discount> discounts = discountRepository.findAllByStudentId(studentId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Discount list successfully found by student id")
                .success(true)
                .data(discountMapper.responseList(discounts))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getDiscountStatisticsMonth() {
        List<DiscountMonthDto> discountMonthDtoList = discountRepository.findAllByMonths();
        return Response.builder()
                .status(HttpStatus.OK)
                .message("GET DISCOUNT STATISTICS MONTH")
                .data(discountMonthDtoList)
                .build();
    }
}
