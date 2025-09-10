package uz.pdp.education.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.dto.UserDto;
import uz.pdp.education.enums.GroupStatus;

import java.time.LocalDate;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class GroupResponse {
    private Long id;
    private String name;
    private Long subjectId;
    private Long teacherId;
    private Double price;
    private UserDto teacherDto;
    private SubjectDto subjectDto;
    private LocalDate startDate;
    private LocalDate endDate;
    private GroupStatus status;
}
