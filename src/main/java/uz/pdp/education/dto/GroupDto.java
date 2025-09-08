package uz.pdp.education.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.education.entity.Student;
import uz.pdp.education.entity.SupportTeacher;
import uz.pdp.education.enums.GroupStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupDto {
    private Long id;
    @NotBlank(message = "name can be null or empty")
    private String name;
    private Set<Student> students;
    private Set<SupportTeacher> supports;
    private LocalDate startDate;
    private LocalDate endDate;
    private GroupStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long createdBy;
}
