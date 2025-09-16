package uz.pdp.education.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAttendanceResponse {
    private Long id;
    private Long groupId;
    private Long lessonId;
    private Long studentId;
    private Boolean active;
}