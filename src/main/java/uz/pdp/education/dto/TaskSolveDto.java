package uz.pdp.education.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskSolveDto {
    private Long id;
    private Long taskId;
    private Long teacherId;
    private Long studentId;
    private Long groupId;
    private String description;
    private Double score;
    private List<String> files = new ArrayList<>();

    @Data
    public static class CreateTaskSolve {
        private Long taskId;
        private Long studentId;
        private String description;
        private List<MultipartFile> files = new ArrayList<>();
    }

    @Data
    public static class SubmitTaskSolve {
        private Long id;
        private Long teacherId;
        private Long groupId;
        private Double score;
    }
}
