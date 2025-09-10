package uz.pdp.education.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.pdp.education.enums.GroupStatus;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupRequest {
    @NotBlank(message = "name can be null or empty")
    private String name;
    private Long subjectId;
    private Long teacherId;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private GroupStatus status;
}
