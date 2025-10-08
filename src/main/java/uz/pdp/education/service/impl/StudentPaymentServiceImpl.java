package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.StudentPaymentDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.entity.StudentBalance;
import uz.pdp.education.entity.StudentPayment;
import uz.pdp.education.enums.BudgetType;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.BudgetMapper;
import uz.pdp.education.mapper.StudentPaymentMapper;
import uz.pdp.education.repository.*;
import uz.pdp.education.service.StudentPaymentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class StudentPaymentServiceImpl implements StudentPaymentService {
    private final AuthUserRepository authUserRepository;
    private final GroupsRepository groupsRepository;
    private final StudentBalanceRepository studentBalanceRepository;
    private final StudentPaymentRepository studentPaymentRepository;
    private final StudentPaymentMapper studentPaymentMapper;
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    @Override
    public Response<?> create(StudentPaymentDto dto) {
        AuthUser student = authUserRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT NOT FOUND"));
        Groups group = groupsRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("GROUP NOT FOUND"));
        /*Subject subject = subjectRepository.findById(group.getSubject().getId())
                .orElseThrow(() -> new ResourceNotFoundException("SUBJECT NOT FOUND"));*/
        StudentBalance studentBalance = studentBalanceRepository.findAllByGroupIdAndStudentId(dto.getGroupId(), dto.getUserId())
                .orElse(null);
        StudentPayment studentPayment = studentPaymentMapper.toEntity(dto);
        studentPayment.setUser(student);
        studentPayment.setGroup(group);
        studentPayment.setYear(LocalDate.now().getYear());
        if (studentBalance != null) {
            double balance = studentBalance.getBalance();
            studentBalance.setBalance(dto.getAmount() + balance);
        } else {
            studentBalance = new StudentBalance();
            studentBalance.setUser(student);
            studentBalance.setGroup(group);
            studentBalance.setBalance(dto.getAmount() - group.getPrice());
        }
        budgetMapper.addBudget("KURSGA TO'LOV", BudgetType.INCOME, dto.getAmount(), budgetRepository);
        studentBalanceRepository.save(studentBalance);
        studentPaymentRepository.save(studentPayment);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Student Payment Created")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        Page<StudentPayment> page = studentPaymentRepository.findAll(pageable);
        if (!page.isEmpty()) {
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .success(true)
                    .pages(page.getTotalPages())
                    .elements(page.getTotalElements())
                    .data(studentPaymentMapper.dtoList(page.getContent()))
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .data(new ArrayList<>())
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getByStudentId(Long studentId, String month) {
        List<StudentPayment> allByUserId = studentPaymentRepository.findAllByUserIdAndMonth(studentId, month);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .data(studentPaymentMapper.dtoList(allByUserId))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> delete(Long id) {
        StudentPayment studentPayment = studentPaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("STUDENTPAYMENT NOT FOUND"));
        studentPaymentRepository.delete(studentPayment);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Successfully deleted")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getByGroupId(Long groupId) {
        List<StudentPayment> allByGroupId = studentPaymentRepository.findAllByGroupId(groupId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("Successfully found by group id")
                .data(studentPaymentMapper.dtoList(allByGroupId))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getStudentPaymentStatisticsMonth() {
        List<Object[]> allByMonthsNative = studentPaymentRepository.findAllByMonthsNative();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("GET STUDENT PAYMENT STATISTICS MONTH")
                .data(studentPaymentMapper.dtoStudentPaymentMonthList(allByMonthsNative))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
